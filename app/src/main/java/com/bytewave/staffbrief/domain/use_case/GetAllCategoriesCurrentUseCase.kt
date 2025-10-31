package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import kotlinx.coroutines.flow.Flow

interface GetAllCategoriesCurrentUseCase{
    suspend operator fun invoke(soldierId: Long?): List<Category>
}

class GetAllCategoriesCurrentUseCaseImpl(
    private  val repository: StaffBriefRepository
) : GetAllCategoriesCurrentUseCase {
    override suspend fun invoke(soldierId: Long?): List<Category> {

        val result = if(soldierId == null) {
            repository.getAllCategoriesCurrent()
        } else {
            repository.getCategoriesBySoldier(soldierId)
        }
        return when(result){
            is Result.Success -> {
                Log.d("Get categories", "Success")
                result.data
            }
            is Result.Error -> {
                Log.d("Get categories", "Error")
                emptyList()
            }
        }

    }
}