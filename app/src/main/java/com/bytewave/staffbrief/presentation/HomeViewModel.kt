package com.bytewave.staffbrief.presentation

import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesCurrentUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllPersonBySoldierUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val getAllPersonBySoldierUseCase: GetAllPersonBySoldierUseCase,
    private val getAllCategoriesCurrentUseCase: GetAllCategoriesCurrentUseCase
): BaseViewModel(getAllCategoriesCurrentUseCase) {
    private val _searchString = MutableStateFlow("")
    val searchQuery:  StateFlow<String> = _searchString.asStateFlow()

    val soldiers: StateFlow<List<Person>> =
        combine(categoryWithIndex, searchQuery) { categories, query ->
            categories to query
        }
            .flatMapLatest { (categories, query) ->
                getAllPersonBySoldierUseCase(categories, query)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun onChangeSearchQuery(query: String) {
        _searchString.value = query
    }
}