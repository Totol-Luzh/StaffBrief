package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface DeleteSoldierUseCase{
    suspend operator fun invoke(id: Long): Int
}

class DeleteSoldierUseCaseImpl(
    private val repository: StaffBriefRepository
) : DeleteSoldierUseCase {
    override suspend fun invoke(id: Long): Int {
        when(val result = repository.deleteSoldier(id)){
            is Result.Success -> {
                Log.d("Delete soldier", "Success")
                return result.data
            }
            is Result.Error -> {
                Log.d("Delete soldier", "Error")
                return -1
            }
        }
    }
}