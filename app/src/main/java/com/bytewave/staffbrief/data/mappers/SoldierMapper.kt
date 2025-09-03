package com.bytewave.staffbrief.data.mappers

import com.bytewave.staffbrief.data.db.entities.SoldiersEntity
import com.bytewave.staffbrief.domain.model.Soldier

fun Soldier.toEntity(): SoldiersEntity = SoldiersEntity(
    soldierId = soldierId,
    personId = personId,
    militaryRank = militaryRank,
    photo = photo,
    info = info,
    positive = positive,
    negative = negative
)

fun SoldiersEntity.toDomain(): Soldier = Soldier(
    soldierId = soldierId,
    personId = personId,
    militaryRank = militaryRank,
    photo = photo,
    info = info,
    positive = positive,
    negative = negative
)