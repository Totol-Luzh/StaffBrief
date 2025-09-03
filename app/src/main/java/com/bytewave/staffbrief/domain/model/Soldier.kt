package com.bytewave.staffbrief.domain.model

import android.graphics.Bitmap
import com.bytewave.staffbrief.data.db.entities.Rank

data class Soldier(
    val soldierId: Long = 0,
    var personId: Long,
    var militaryRank: Rank,
    var photo: Bitmap? = null,
    var info: String? = null,
    var positive: String? = null,
    var negative: String? = null
)
