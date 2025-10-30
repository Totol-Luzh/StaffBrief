package com.bytewave.staffbrief.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.data.db.entities.Rank
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.use_case.AddPersonUseCase
import com.bytewave.staffbrief.domain.use_case.AddSoldierUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesCurrentUseCase
import com.bytewave.staffbrief.domain.use_case.InsertSoldiersCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateSoldierViewModel(
    private val addPersonUseCase: AddPersonUseCase,
    private val addSoldierUseCase: AddSoldierUseCase,
    private val getAllCategoriesCurrentUseCase: GetAllCategoriesCurrentUseCase,
    private val insertSoldiersCategoriesUseCase: InsertSoldiersCategoriesUseCase
) : BaseViewModel(getAllCategoriesCurrentUseCase) {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _patronymic = MutableStateFlow("")
    val patronymic: StateFlow<String> = _patronymic.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _phoneNumber= MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _info= MutableStateFlow("")
    val info: StateFlow<String> = _info.asStateFlow()

    private val _positive= MutableStateFlow("")
    val positive: StateFlow<String> = _positive.asStateFlow()

    private val _negative= MutableStateFlow("")
    val negative: StateFlow<String> = _negative.asStateFlow()

    fun onFirstNameChange(newValue: String) {
        _firstName.value = newValue
    }

    fun onLastNameChange(newValue: String) {
        _lastName.value = newValue
    }

    fun onPatronymicChange(newValue: String) {
        _patronymic.value = newValue
    }

    fun onBirthDateChange(newValue: String){
        _birthDate.value = newValue
    }

    fun onPhoneNumberChange(newValue: String){
        _phoneNumber.value = newValue
    }

    fun onInfoChange(newValue: String){
        _info.value = newValue
    }

    fun onPositiveChange(newValue: String){
        _positive.value = newValue
    }

    fun onNegativeChange(newValue: String){
        _negative.value = newValue
    }

    fun addSoldier(){
        if(_lastName.value.isNotBlank() && _firstName.value.isNotBlank() && _patronymic.value.isNotBlank()){
            viewModelScope.launch {
                try {
                    val personId = addPersonUseCase(
                        Person(
                            firstName = _firstName.value,
                            patronymic = _patronymic.value,
                            lastName = _lastName.value,
                            birthDate = if (_firstName.value.isNotBlank()) _birthDate.value else null,
                            phoneNumber = if (_phoneNumber.value.isNotBlank()) _phoneNumber.value else null
                        )
                    )
                    val soldierId = addSoldierUseCase(
                        Soldier(
                            personId = personId,
                            militaryRank = Rank.SOLDIER,
                            photo = null,
                            info = if (_info.value.isNotBlank()) _info.value else null,
                            positive = if (_positive.value.isNotBlank()) _positive.value else null,
                            negative = if (_negative.value.isNotBlank()) _negative.value else null
                        )
                    )
                    insertSoldiersCategoriesUseCase(soldierId, categoryWithIndex.value)
                } catch (e: Exception){
                    Log.e("Error Add Person", "${e.message}")
                }
            }
        }
    }


}