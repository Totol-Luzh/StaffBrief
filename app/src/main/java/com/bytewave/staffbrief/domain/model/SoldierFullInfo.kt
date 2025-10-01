package com.bytewave.staffbrief.domain.model

import com.bytewave.staffbrief.data.db.entities.Rank

data class SoldierFullInfo(
    val soldierId: Long,
    val personId: Long,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val birthDate: String?,
    val phoneNumber: String?,
    val rank: Rank,
    val info: String?,
    val positive: String?,
    val negative: String?
)
