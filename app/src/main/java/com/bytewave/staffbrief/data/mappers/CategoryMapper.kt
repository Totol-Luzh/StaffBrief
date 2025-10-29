package com.bytewave.staffbrief.data.mappers

import androidx.compose.ui.graphics.Color
import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.domain.model.Category

fun Category.toEntity(): CategoriesEntity = CategoriesEntity(
    categoryId = id,
    name = name,
    priority = priority,
    color = color.value.toInt()
)

fun CategoriesEntity.toDomain(): Category = Category(
    id = categoryId,
    name = name,
    priority = priority,
    color = Color(color)
)