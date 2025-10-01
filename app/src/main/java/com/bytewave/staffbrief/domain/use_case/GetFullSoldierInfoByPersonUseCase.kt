package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.model.SoldierFullInfo
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface GetFullSoldierInfoByPersonUseCase {
    suspend operator fun invoke(personId: Long): SoldierFullInfo?
}

class GetFullSoldierInfoByPersonUseCaseImpl(
    private val repository: StaffBriefRepository
): GetFullSoldierInfoByPersonUseCase{
    override suspend fun invoke(personId: Long): SoldierFullInfo? {
        when(val result = repository.getFullSoldierInfoByPerson(personId)){
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