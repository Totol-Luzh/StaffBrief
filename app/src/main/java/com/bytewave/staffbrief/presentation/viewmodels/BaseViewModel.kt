package com.bytewave.staffbrief.presentation.viewmodels

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

    fun onChangeCategoryFlag(category: Category) {

        _categoryWithIndex.value = categoryWithIndex.value.map { item ->
            val currentCategory = item.first

            when{
                currentCategory.name == category.name -> {
                    item.copy(second =  !item.second)
                }
                category.id == 0 && currentCategory.name != category.name -> {
                    item.copy(second = false)
                }
                category.id != 0 && currentCategory.id == 0 -> {
                    item.copy(second = false)
                }
                else -> item
            }
        }
    }
    open fun loadCategories(){
        viewModelScope.launch {
            _categoryWithIndex.value = getAllCategoriesCurrentUseCase(null).map {
                    element ->
                Pair(element, false)
            }
        }
    }
}