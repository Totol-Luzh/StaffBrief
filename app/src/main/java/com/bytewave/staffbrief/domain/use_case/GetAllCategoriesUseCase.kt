package com.bytewave.staffbrief.domain.use_case

import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import kotlinx.coroutines.flow.Flow

interface GetAllCategoriesUseCase{
    operator fun invoke(): Flow<List<CategoriesEntity>>
}

class GetAllCategoriesUseCaseImpl(
    private  val repository: StaffBriefRepository
) : GetAllCategoriesUseCase {
    override fun invoke(): Flow<List<CategoriesEntity>> = repository.getAllCategories()
}