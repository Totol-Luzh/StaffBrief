package com.bytewave.staffbrief.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.data.DataStoreManager
import kotlinx.coroutines.launch

class ThemeViewModel(private val datastoreManager: DataStoreManager) : ViewModel() {

    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    init {
        viewModelScope.launch {
            datastoreManager.loadTheme().collect { theme ->
                _isDarkTheme.value = theme
            }
        }
    }

    fun toggleTheme() {
        val newTheme = !_isDarkTheme.value
        _isDarkTheme.value = newTheme
        viewModelScope.launch {
            datastoreManager.saveTheme(newTheme)
        }
    }
}