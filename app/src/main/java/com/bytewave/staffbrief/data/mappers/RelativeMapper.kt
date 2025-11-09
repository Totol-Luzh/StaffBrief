package com.bytewave.staffbrief.data.mappers

import com.bytewave.staffbrief.data.db.entities.RelativesEntity
import com.bytewave.staffbrief.domain.model.Relative

fun Relative.toEntity(): RelativesEntity = RelativesEntity(
    relativeId = id,
    soldierId = soldierId ?: 0,
    fullName = fullName,
    kinship = kinship,
    info = info
)

fun RelativesEntity.toDomain(): Relative = Relative(
    id = relativeId,
    soldierId = soldierId,
    fullName = fullName,
    kinship = kinship,
    info = info
)