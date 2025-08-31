package com.bytewave.staffbrief.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.domain.use_case.AddCategoryUseCase
import com.bytewave.staffbrief.domain.use_case.DeleteCategoryUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryManagementViewModel(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _categoryName = MutableStateFlow("")
    val categoryName: StateFlow<String> = _categoryName.asStateFlow()

    private val _categoryPriority = MutableStateFlow<Byte>(10)
    val categoryPriority: StateFlow<Byte> = _categoryPriority.asStateFlow()


    val categories: StateFlow<List<CategoriesEntity>> = getAllCategoriesUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun onCategoryNameChange(newValue: String) {
        _categoryName.value = newValue
    }

    fun onPriorityChange(newValue: String) {
        val number = newValue.toIntOrNull()
        if (number != null && number in 1 ..Byte.MAX_VALUE) {
            _categoryPriority.value = number.toByte()
        }
    }

    fun addCategory(){
        val name = _categoryName.value
        val priority = _categoryPriority
        if (name.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val id = addCategoryUseCase(name, priority.value)
                } catch (e: Exception){
                    Log.e("Error Add Category", "${e.message}")
                }
            }
            _categoryName.value = ""
            _categoryPriority.value = 10
        }
    }

    fun deleteCategory(categoryId: Int){
        viewModelScope.launch {
            try {
                val id = deleteCategoryUseCase(categoryId)
            } catch (e: Exception){
                Log.e("Error Delete Category", "${e.message}")
            }
        }
    }
}