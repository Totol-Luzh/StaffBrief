package com.bytewave.staffbrief.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bytewave.staffbrief.data.db.entities.Rank
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.model.SoldierFullInfo
import com.bytewave.staffbrief.domain.use_case.AddPersonUseCase
import com.bytewave.staffbrief.domain.use_case.AddSoldierUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesCurrentUseCase
import com.bytewave.staffbrief.domain.use_case.GetFullSoldierInfoByPersonUseCase
import com.bytewave.staffbrief.domain.use_case.InsertSoldiersCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SoldierFormViewModel(
    private val addPersonUseCase: AddPersonUseCase,
    private val addSoldierUseCase: AddSoldierUseCase,
    private val getAllCategoriesCurrentUseCase: GetAllCategoriesCurrentUseCase,
    private val insertSoldiersCategoriesUseCase: InsertSoldiersCategoriesUseCase,
    private val getFullSoldierInfoByPersonUseCase: GetFullSoldierInfoByPersonUseCase
) : BaseViewModel(getAllCategoriesCurrentUseCase) {

    private val _soldierState = MutableStateFlow(SoldierFullInfo())
    val soldierState: StateFlow<SoldierFullInfo> = _soldierState.asStateFlow()

    fun onFirstNameChange(newValue: String) {
        _soldierState.value = _soldierState.value.copy(firstName = newValue)
    }

    fun onLastNameChange(newValue: String) {
        _soldierState.value = _soldierState.value.copy(lastName = newValue)
    }

    fun onPatronymicChange(newValue: String) {
        _soldierState.value = _soldierState.value.copy(patronymic = newValue)
    }

    fun onBirthDateChange(newValue: String){
        _soldierState.value = _soldierState.value.copy(birthDate = newValue)
    }

    fun onPhoneNumberChange(newValue: String){
        _soldierState.value = _soldierState.value.copy(phoneNumber = newValue)
    }

    fun onInfoChange(newValue: String){
        _soldierState.value = _soldierState.value.copy(info = newValue)
    }

    fun onPositiveChange(newValue: String){
        _soldierState.value = _soldierState.value.copy(positive = newValue)
    }

    fun onNegativeChange(newValue: String){
        _soldierState.value = _soldierState.value.copy(negative = newValue)
    }

    fun onRankChange(newValue: Rank){
        _soldierState.value = _soldierState.value.copy(rank = newValue)
    }

    fun onPhotoChange(newValue: Bitmap?){
        _soldierState.value = _soldierState.value.copy(photo = newValue)
    }

    fun addSoldier(){
        if(soldierState.value.lastName.isNotBlank() && soldierState.value.firstName.isNotBlank() && soldierState.value.patronymic.isNotBlank()){
            viewModelScope.launch {
                try {
                    var person = Person(
                        id =soldierState.value.personId,
                        firstName = soldierState.value.firstName,
                        patronymic = soldierState.value.patronymic,
                        lastName = soldierState.value.lastName,
                        birthDate = if (soldierState.value.birthDate.isNullOrBlank()) null else soldierState.value.birthDate,
                        phoneNumber = if (soldierState.value.phoneNumber.isNullOrBlank()) null else soldierState.value.phoneNumber
                    )
                    val soldier = Soldier(
                        soldierId = soldierState.value.soldierId,
                        personId = soldierState.value.personId,
                        militaryRank = soldierState.value.rank,
                        photo = soldierState.value.photo,
                        info = if (soldierState.value.info.isNullOrBlank()) null else soldierState.value.info,
                        positive = if (soldierState.value.positive.isNullOrBlank()) null else soldierState.value.positive,
                        negative = if (soldierState.value.negative.isNullOrBlank()) null else soldierState.value.negative
                    )
                    if(soldierState.value.personId == 0L) {
                        soldier.personId = addPersonUseCase(person)
                        val soldierId = addSoldierUseCase(soldier)
                        insertSoldiersCategoriesUseCase(soldierId, categoryWithIndex.value)
                    } else {
                        addPersonUseCase(person)
                        addSoldierUseCase(soldier)
                        insertSoldiersCategoriesUseCase(soldier.soldierId, categoryWithIndex.value)
                    }


                } catch (e: Exception){
                    Log.e("Error Add Person", "${e.message}")
                }
            }
        }
    }

    fun loadSoldier(id: Long){
        viewModelScope.launch {
            val soldier = getFullSoldierInfoByPersonUseCase(personId = id)
            if (soldier != null) {
                _soldierState.value = soldier
            }
            val categories = getAllCategoriesCurrentUseCase(soldierState.value.soldierId).toSet()
            _categoryWithIndex.value = categoryWithIndex.value.map {(category, flag) ->
                if(category in categories) {
                    category to true
                } else {
                    category to false
                }

            }
        }
    }
}