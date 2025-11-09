package com.bytewave.staffbrief.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.domain.model.Relative
import com.bytewave.staffbrief.domain.model.SoldierFullInfo
import com.bytewave.staffbrief.domain.use_case.GetFullSoldierInfoByPersonUseCase
import com.bytewave.staffbrief.domain.use_case.GetRelativesBySoldierUseCase
import kotlinx.coroutines.launch

class SoldierScreenViewModel(
    private val getFullSoldierInfoByPersonUseCase: GetFullSoldierInfoByPersonUseCase,
    private val getRelativesBySoldierUseCase: GetRelativesBySoldierUseCase
): ViewModel(){
    private val _soldier = mutableStateOf<SoldierFullInfo?>(null)
    val soldier: State<SoldierFullInfo?> = _soldier

    private val _relatives = mutableStateOf<List<Relative>>(listOf())
    val relatives: State<List<Relative>> = _relatives
    fun loadSoldier(personId: Long) {
        viewModelScope.launch {
            _soldier.value = getFullSoldierInfoByPersonUseCase(personId)
            soldier.value?.let{
                _relatives.value = getRelativesBySoldierUseCase(it.soldierId)
            }
        }
    }
}