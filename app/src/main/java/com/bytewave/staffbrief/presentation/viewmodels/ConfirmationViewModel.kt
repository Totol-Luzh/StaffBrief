package com.bytewave.staffbrief.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class ConfirmationViewModel() : ViewModel() {
    private val _confirmation = MutableStateFlow<Pair<Long?, Boolean>>(Pair(null, false))
    val confirmation: StateFlow<Pair<Long?, Boolean>> = _confirmation.asStateFlow()

    fun onConfirm(index: Long?, confirmation: Boolean){
        _confirmation.value = Pair(index, confirmation)
    }
}