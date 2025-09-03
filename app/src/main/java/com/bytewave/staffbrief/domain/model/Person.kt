package com.bytewave.staffbrief.domain.model

data class Person(
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val birthDate: String? = null,
    val phoneNumber: String? = null
)
