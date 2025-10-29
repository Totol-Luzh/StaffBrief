package com.bytewave.staffbrief.domain.model

import androidx.compose.ui.graphics.Color

data class Category(
    val id: Int,
    val name: String,
    val priority: Byte,
    val color: Color
)