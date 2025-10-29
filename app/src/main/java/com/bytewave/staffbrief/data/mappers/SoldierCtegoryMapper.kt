package com.bytewave.staffbrief.data.mappers

import com.bytewave.staffbrief.data.db.entities.SoldiersCategoriesEntity
import com.bytewave.staffbrief.domain.model.SoldierCategory

fun SoldierCategory.toEntity(): SoldiersCategoriesEntity = SoldiersCategoriesEntity(
    soldierCategoryId = id,
    soldierId = soldierId,
    categoryId = categoryId
)

fun SoldiersCategoriesEntity.toDomain(): SoldierCategory = SoldierCategory(
    id = soldierCategoryId,
    soldierId = soldierId,
    categoryId = categoryId
)