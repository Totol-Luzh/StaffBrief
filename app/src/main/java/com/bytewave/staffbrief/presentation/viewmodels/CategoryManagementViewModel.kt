package com.bytewave.staffbrief.presentation.viewmodels

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.domain.model.Category
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

    private val _category = MutableStateFlow(Category())
    val category: StateFlow<Category> = _category.asStateFlow()

    private val _confirmDelete = MutableStateFlow<Pair<Category?, Boolean>>(Pair(null, false))
    val confirmDelete: StateFlow<Pair<Category?, Boolean>> = _confirmDelete.asStateFlow()

    val categories: StateFlow<List<Category>> = getAllCategoriesUseCase().stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        emptyList()
    )

    fun onCategoryNameChange(newValue: String) {
        _category.value = _category.value.copy(name = newValue)
    }

    fun onPriorityChange(newValue: String) {
        if (newValue.isEmpty()) {
            _category.value = _category.value.copy(priority = null)
            return
        }
        val number = newValue.toIntOrNull()
        if (number != null && number in 1..Byte.MAX_VALUE) {
            _category.value = _category.value.copy(priority = number.toByte())
        }
    }

    fun onColorChange(newValue: Color){
        _category.value = _category.value.copy(color = newValue)
    }

    fun onConfirmDelete(category: Category?, confirmation: Boolean){
        _confirmDelete.value = Pair(category, confirmation)
    }

    fun editCategory(newValue: Category){
        _category.value = newValue
    }

    fun newCategory(){
        _category.value = Category()
    }

    fun addCategory(): Boolean {
         val cat = _category.value
        return if(cat.name.isNotBlank() && cat.priority != null) {
            viewModelScope.launch {
                try {
                    addCategoryUseCase(cat)
                } catch (e: Exception){
                    Log.e("Error Add Category", "${e.message}")
                }
            }
            _category.value = Category()
            true
        } else false
    }

    fun deleteCategory(categoryId: Int){
        viewModelScope.launch {
            try {
                _confirmDelete.value = Pair(null, false)
                val id = deleteCategoryUseCase(categoryId)
            } catch (e: Exception){
                Log.e("Error Delete Category", "${e.message}")
            }
        }
    }
}