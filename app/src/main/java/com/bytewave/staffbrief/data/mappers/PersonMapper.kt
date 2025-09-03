package com.bytewave.staffbrief.data.mappers

import com.bytewave.staffbrief.data.db.entities.PersonsEntity
import com.bytewave.staffbrief.domain.model.Person

fun Person.toEntity(): PersonsEntity = PersonsEntity(
    personId = id,
    firstName = firstName,
    lastName = lastName,
    patronymic = patronymic,
    birthDate = birthDate,
    phoneNumber = phoneNumber
)

fun PersonsEntity.toDomain(): Person = Person(
    id = personId,
    firstName = firstName,
    lastName = lastName,
    patronymic = patronymic,
    birthDate = birthDate,
    phoneNumber = phoneNumber
)