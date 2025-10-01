package com.bytewave.staffbrief.domain.use_case

import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import kotlinx.coroutines.flow.Flow

interface GetAllPersonBySoldierUseCase{
    operator fun invoke(): Flow<List<Person>>
}

class GetAllSoldierFullInfoUseCaseImpl(
    private  val repository: StaffBriefRepository
) : GetAllPersonBySoldierUseCase {
    override fun invoke(): Flow<List<Person>> = repository.getAllSoldierFullInfo()
}