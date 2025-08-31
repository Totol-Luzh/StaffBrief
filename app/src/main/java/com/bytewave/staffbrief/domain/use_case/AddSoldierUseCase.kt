package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.data.db.entities.Rank
import com.bytewave.staffbrief.data.db.entities.SoldiersEntity
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import com.bytewave.staffbrief.domain.repository.StaffBriefRepositoryImpl


interface AddSoldierUseCase{
    suspend operator fun invoke(): Long
}

class AddSoldierUseCaseImpl(
    private val repository: StaffBriefRepositoryImpl
) : AddSoldierUseCase {
    override suspend fun invoke(): Long {
        when(val  result = repository.addSoldier(
            SoldiersEntity(
                soldierId = 0,
                //personId = 123L,
                militaryRank = Rank.CAPTAIN,
                photo = null,
                info = "Отличный стратег",
                positive = "Ответственный, дисциплинированный",
                negative = "Иногда опаздывает"
            )
        )){
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