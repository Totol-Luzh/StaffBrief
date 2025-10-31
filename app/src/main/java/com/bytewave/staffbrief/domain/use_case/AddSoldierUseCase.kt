package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository


interface AddSoldierUseCase{
    suspend operator fun invoke(soldier: Soldier): Long
}

class AddSoldierUseCaseImpl(
    private val repository: StaffBriefRepository
) : AddSoldierUseCase {
    override suspend fun invoke(soldier: Soldier): Long {
        if(soldier.soldierId == 0L) {
            return when (val result = repository.addSoldier(soldier)) {
                is Result.Success -> {
                    Log.d("Add Soldier", "Success")
                    result.data
                }

                is Result.Error -> {
                    Log.d("Add Soldier", "Error")
                    -1
                }
            }
        } else {
            return when (val result = repository.updateSoldier(soldier)) {
                is Result.Success -> {
                    Log.d("Add Soldier", "Success")
                    result.data.toLong()
                }

                is Result.Error -> {
                    Log.d("Add Soldier", "Error")
                    -1
                }
            }
        }
    }
}