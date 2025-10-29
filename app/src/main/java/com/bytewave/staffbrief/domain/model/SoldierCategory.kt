package com.bytewave.staffbrief.domain.model

data class SoldierCategory(
    val id: Long = 0L,
    val soldierId: Long,
    val categoryId: Int
)
