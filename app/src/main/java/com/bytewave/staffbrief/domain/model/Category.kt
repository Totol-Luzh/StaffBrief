package com.bytewave.staffbrief.domain.model

import androidx.compose.ui.graphics.Color

data class Category(
    val id: Int = 0,
    val name: String = "",
    val priority: Byte? = null,
    val color: Color = Color.White
)