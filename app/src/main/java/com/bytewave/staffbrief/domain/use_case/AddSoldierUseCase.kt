package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.data.db.entities.Rank
import com.bytewave.staffbrief.data.db.entities.SoldiersEntity
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import com.bytewave.staffbrief.domain.repository.StaffBriefRepositoryImpl


interface AddSoldierUseCase{
    suspend operator fun invoke(soldier: Soldier): Long
}

class AddSoldierUseCaseImpl(
    private val repository: StaffBriefRepository
) : AddSoldierUseCase {
    override suspend fun invoke(soldier: Soldier): Long {
        when(val  result = repository.addSoldier(soldier)){
            is Result.Success -> {
                Log.d("Add Soldier", "Success")
                return result.data
            }
            is Result.Error -> {
                Log.d("Add Soldier", "Error")
                return -1
            }
        }

    }
}