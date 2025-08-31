package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface DeleteCategoryUseCase{
    suspend operator fun invoke(id: Int): Int
}

class DeleteCategoryUseCaseImpl(
    private val repository: StaffBriefRepository
) : DeleteCategoryUseCase {
    override suspend fun invoke(id: Int): Int {
        when(val result = repository.deleteCategory(id)){
            is Result.Success -> {
                Log.d("Add Category", "Success")
                return result.data
            }
            is Result.Error -> {
                Log.d("Add Category", "Error")
                return -1
            }
        }
    }
}