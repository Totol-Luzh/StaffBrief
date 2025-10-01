package com.bytewave.staffbrief.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.use_case.GetAllPersonBySoldierUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val getAllPersonBySoldierUseCase: GetAllPersonBySoldierUseCase
): ViewModel() {
    val soldiers: StateFlow<List<Person>> = getAllPersonBySoldierUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
}