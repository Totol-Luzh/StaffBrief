package com.bytewave.staffbrief.domain.model

import android.graphics.Bitmap
import com.bytewave.staffbrief.data.db.entities.Rank

data class SoldierFullInfo(
    val soldierId: Long = 0L,
    val personId: Long = 0L,
    val firstName: String = "",
    val lastName: String = "",
    val patronymic: String = "",
    val birthDate: String? = null,
    val phoneNumber: String? = null,
    val photo: Bitmap? = null,
    val rank: Rank = Rank.SOLDIER,
    val info: String? = null,
    val positive: String? = null,
    val negative: String? = null
)
