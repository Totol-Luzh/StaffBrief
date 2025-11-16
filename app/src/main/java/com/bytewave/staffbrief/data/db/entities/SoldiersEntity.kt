package com.bytewave.staffbrief.data.db.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "soldiers",
        indices = [Index(value = ["first_name", "last_name", "patronymic", "birth_date"], unique = true)]
)
data class SoldiersEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val soldierId: Long = 0,

        @ColumnInfo(name = "first_name")
        var firstName: String,

        @ColumnInfo(name = "last_name")
        var lastName: String,

        @ColumnInfo(name = "patronymic")
        var patronymic: String,

        @ColumnInfo(name = "birth_date")
        var birthDate: String? = null,

        @ColumnInfo(name = "phone_number")
        var phoneNumber: String? = null,

        @ColumnInfo(name = "military_rank")
        var militaryRank: Rank,

        var photo: Bitmap? = null,

        @ColumnInfo(name = "info")
        var info: String? = null,

        @ColumnInfo(name = "positive")
        var positive: String? = null,

        @ColumnInfo(name = "negative")
        var negative: String? = null
)