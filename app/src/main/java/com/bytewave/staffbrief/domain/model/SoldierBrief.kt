package com.bytewave.staffbrief.domain.model

import android.graphics.Bitmap
import com.bytewave.staffbrief.data.db.entities.Rank

data class SoldierBrief(
    val soldierId: Long,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val rank: Rank,
    val photo: Bitmap?
)