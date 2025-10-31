package com.bytewave.staffbrief.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesCurrentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BaseViewModel(
    private val getAllCategoriesCurrentUseCase: GetAllCategoriesCurrentUseCase
) : ViewModel() {
    protected val _categoryWithIndex = MutableStateFlow<List<Pair<Category, Boolean>>>(emptyList())
    val categoryWithIndex: StateFlow<List<Pair<Category, Boolean>>> = _categoryWithIndex

    init {
        viewModelScope.launch {
            _categoryWithIndex.value = getAllCategoriesCurrentUseCase(null).map {
                    element ->
                Pair(element, false)
            }
        }
    }
    fun onChangeCategoryFlag(category: Category, flag: Boolean) {
        _categoryWithIndex.value = categoryWithIndex.value.map { item ->
            if (item.first.name == category.name) {
                item.copy(category, flag)
            } else {
                item
            }
        }
    }
    fun updateCategoryList(){
        viewModelScope.launch {
            _categoryWithIndex.value = getAllCategoriesCurrentUseCase(null).map {
                    element ->
                Pair(element, false)
            }
        }
    }
}