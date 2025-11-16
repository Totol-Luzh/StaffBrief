package com.bytewave.staffbrief.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.domain.model.Relative
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.use_case.GetRelativesBySoldierUseCase
import com.bytewave.staffbrief.domain.use_case.GetSoldierByIdUseCase
import kotlinx.coroutines.launch

class SoldierScreenViewModel(
    private val getSoldierByIdUseCase: GetSoldierByIdUseCase,
    private val getRelativesBySoldierUseCase: GetRelativesBySoldierUseCase
): ViewModel(){
    private val _soldier = mutableStateOf<Soldier?>(null)
    val soldier: State<Soldier?> = _soldier

    private val _relatives = mutableStateOf<List<Relative>>(listOf())
    val relatives: State<List<Relative>> = _relatives
    fun loadSoldier(personId: Long) {
        viewModelScope.launch {
            _soldier.value = getSoldierByIdUseCase(personId)
            soldier.value?.let{
                _relatives.value = getRelativesBySoldierUseCase(it.soldierId)
            }
        }
    }
}