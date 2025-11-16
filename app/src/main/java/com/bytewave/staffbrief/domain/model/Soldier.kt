package com.bytewave.staffbrief.domain.model

import android.graphics.Bitmap
import com.bytewave.staffbrief.data.db.entities.Rank

data class Soldier(
    val soldierId: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val patronymic: String = "",
    val birthDate: String? = null,
    val phoneNumber: String? = null,
    var militaryRank: Rank = Rank.SOLDIER,
    var photo: Bitmap? = null,
    var info: String? = null,
    var positive: String? = null,
    var negative: String? = null
)
