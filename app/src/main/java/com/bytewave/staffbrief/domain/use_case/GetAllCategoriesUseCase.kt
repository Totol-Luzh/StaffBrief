package com.bytewave.staffbrief.domain.use_case

import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import kotlinx.coroutines.flow.Flow

interface GetAllCategoriesUseCase{
    operator fun invoke(): Flow<List<Category>>
}

class GetAllCategoriesUseCaseImpl(
    private  val repository: StaffBriefRepository
) : GetAllCategoriesUseCase {
    override fun invoke(): Flow<List<Category>> = repository.getAllCategories()
}