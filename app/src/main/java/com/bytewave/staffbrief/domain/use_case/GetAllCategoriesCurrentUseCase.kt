package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import kotlinx.coroutines.flow.Flow

interface GetAllCategoriesCurrentUseCase{
    suspend operator fun invoke(): List<Category>
}

class GetAllCategoriesCurrentUseCaseImpl(
    private  val repository: StaffBriefRepository
) : GetAllCategoriesCurrentUseCase {
    override suspend fun invoke(): List<Category> {
        val result = repository.getAllCategoriesCurrent()
        return when(result){
            is Result.Success -> {
                Log.d("Get current categories", "Success")
                result.data
            }
            is Result.Error -> {
                Log.d("Get current categories", "Error")
                emptyList()
            }
        }

    }
}