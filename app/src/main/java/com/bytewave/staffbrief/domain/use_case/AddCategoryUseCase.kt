package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface AddCategoryUseCase{
    suspend operator fun invoke(name: String, priority: Byte, color: Color): Int
}

class AddCategoryUseCaseImpl(
    private val repository: StaffBriefRepository
) : AddCategoryUseCase {
    override suspend fun invoke(name: String, priority: Byte, color: Color): Int {
        when(val result = repository.addCategory(
            Category(
                id = 0,
                name = name,
                priority = priority,
                color = color
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