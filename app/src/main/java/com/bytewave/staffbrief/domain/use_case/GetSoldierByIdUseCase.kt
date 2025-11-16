package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface GetSoldierByIdUseCase {
    suspend operator fun invoke(personId: Long): Soldier?
}

class GetSoldierByIdUseCaseImpl(
    private val repository: StaffBriefRepository
): GetSoldierByIdUseCase{
    override suspend fun invoke(personId: Long): Soldier? {
        when(val result = repository.getSoldierById(personId)){
            is Result.Success -> {
                Log.d("Get soldier", "Success")
                return result.data
            }
            is Result.Error -> {
                Log.d("Get soldier", "Error")
                return null
            }
        }
    }
}