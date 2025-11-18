package com.bytewave.staffbrief.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.Relative
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesCurrentUseCase
import com.bytewave.staffbrief.domain.use_case.GetRelativesBySoldierUseCase
import com.bytewave.staffbrief.domain.use_case.GetSoldierByIdUseCase
import kotlinx.coroutines.launch

class SoldierScreenViewModel(
    private val getSoldierByIdUseCase: GetSoldierByIdUseCase,
    private val getRelativesBySoldierUseCase: GetRelativesBySoldierUseCase,
    private val getAllCategoriesCurrentUseCase: GetAllCategoriesCurrentUseCase
): ViewModel(){
    private val _soldier = mutableStateOf<Soldier?>(null)
    val soldier: State<Soldier?> = _soldier

    private val _relatives = mutableStateOf<List<Relative>>(listOf())
    val relatives: State<List<Relative>> = _relatives

    private val _categories = mutableStateOf<List<Category>>(listOf())
    val categories: State<List<Category>> = _categories

    fun loadSoldier(soldierId: Long) {
        viewModelScope.launch {
            _soldier.value = getSoldierByIdUseCase(soldierId)
            _relatives.value = getRelativesBySoldierUseCase(soldierId)
            _categories.value = getAllCategoriesCurrentUseCase(soldierId)
        }
    }
}