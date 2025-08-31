package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface AddCategoryUseCase{
    suspend operator fun invoke(name: String, priority: Byte): Int
}

class AddCategoryUseCaseImpl(
    private val repository: StaffBriefRepository
) : AddCategoryUseCase {
    override suspend fun invoke(name: String, priority: Byte): Int {
        when(val result = repository.addCategory(
            CategoriesEntity(
                categoryId = 0,
                name = name,
                priority = priority
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