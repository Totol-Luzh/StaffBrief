package com.bytewave.staffbrief.presentation

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

    private val _categoryName = MutableStateFlow("")
    val categoryName: StateFlow<String> = _categoryName.asStateFlow()

    private val _categoryPriority = MutableStateFlow<Byte?>(10)
    val categoryPriority: StateFlow<Byte?> = _categoryPriority.asStateFlow()

    private val _confirmDelete = MutableStateFlow<Pair<Category?, Boolean>>(Pair(null, false))
    val confirmDelete: StateFlow<Pair<Category?, Boolean>> = _confirmDelete.asStateFlow()

    val categories: StateFlow<List<Category>> = getAllCategoriesUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun onCategoryNameChange(newValue: String) {
        _categoryName.value = newValue
    }

    fun onPriorityChange(newValue: String) {
        if (newValue.isEmpty()) {
            _categoryPriority.value = null
            return
        }
        val number = newValue.toIntOrNull()
        if (number != null && number in 1..Byte.MAX_VALUE) {
            _categoryPriority.value = number.toByte()
        }
    }

    fun onConfirmChange(category: Category?, confirmation: Boolean){
        _confirmDelete.value = Pair(category, confirmation)
    }

    fun addCategory(): Boolean {
        val name = _categoryName.value
        val priority = _categoryPriority.value
        return if (name.isNotBlank() && priority != null) {
            viewModelScope.launch {
                try {
                    addCategoryUseCase(name, priority, Color.White)
                } catch (e: Exception){
                    Log.e("Error Add Category", "${e.message}")
                }
            }
            _categoryName.value = ""
            _categoryPriority.value = 10
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