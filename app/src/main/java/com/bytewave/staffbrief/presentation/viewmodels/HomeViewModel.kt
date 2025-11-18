package com.bytewave.staffbrief.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.SoldierBrief
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesCurrentUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllSoldierUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllSoldierUseCase: GetAllSoldierUseCase,
    private val getAllCategoriesCurrentUseCase: GetAllCategoriesCurrentUseCase
): BaseViewModel(getAllCategoriesCurrentUseCase) {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val soldiers: StateFlow<List<SoldierBrief>> =
        combine(categoryWithIndex, searchQuery) { categories, query ->
            categories to query
        }
            .flatMapLatest { (categories, query) ->
                getAllSoldierUseCase(categories, query)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.Companion.WhileSubscribed(5000),
                emptyList()
            )

    fun onChangeSearchQuery(query: String) {
        _searchQuery.value = query
    }
    fun clearSearchQuery(){
        _searchQuery.value = ""
    }

    override fun loadCategories(){
        viewModelScope.launch {
             val categories = getAllCategoriesCurrentUseCase(null).map { it to false}
            _categoryWithIndex.value = if(categories.isEmpty()) categories else categories + Pair(
                Category(0, "", priority = 0), false)
        }
    }
}