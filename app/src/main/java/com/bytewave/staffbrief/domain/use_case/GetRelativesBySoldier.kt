package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.model.Relative
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface GetRelativesBySoldierUseCase{
    suspend operator fun invoke(soldierId: Long): List<Relative>
}

class GetRelativesBySoldierUseCaseImpl(
    private val repository: StaffBriefRepository
) : GetRelativesBySoldierUseCase{
    override suspend fun invoke(soldierId: Long): List<Relative> {
        return when(val result = repository.getRelativesBySoldier(soldierId)){
            is Result.Success -> {
                Log.d("Get relatives", "Success")
                result.data
            }
            is Result.Error -> {
                Log.d("Get relatives", "Error")
                emptyList()
            }
        }
    }
}