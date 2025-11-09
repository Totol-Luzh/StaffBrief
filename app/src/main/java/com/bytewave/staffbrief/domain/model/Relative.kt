package com.bytewave.staffbrief.domain.model

data class Relative(
    val id: Long = 0L,
    val soldierId: Long? = null,
    val fullName: String = "",
    val kinship: String = "",
    val info: String = ""
)
