package com.bytewave.staffbrief.domain.use_case

import com.bytewave.staffbrief.data.db.entities.SoldiersEntity
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface GetSoldiersUseCase{
    suspend operator fun invoke(): List<SoldiersEntity>
}

//class GetSoldiersUseCaseImpl(
//    private val staffBriefRepository: StaffBriefRepository
//) : GetSoldiersUseCase {
//    override suspend fun invoke(): List<SoldiersEntity> {
//        //return staffBriefRepository.
//    }
//}