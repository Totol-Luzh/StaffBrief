package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface AddCategoryUseCase{
    suspend operator fun invoke(category: Category): Int
}

class AddCategoryUseCaseImpl(
    private val repository: StaffBriefRepository
) : AddCategoryUseCase {
    override suspend fun invoke(category: Category): Int {
        when(val result = repository.addCategory(
            Category(
                id = 0,
                name = category.name,
                priority = category.priority,
                color = category.color
            )
        )){
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