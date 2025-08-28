package com.bytewave.staffbrief.data.db.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "soldiers",
        foreignKeys = [
                    ForeignKey(
                                    entity = CategoriesEntity::class,
                                    parentColumns = ["id"],
                                    childColumns = ["category_id"],
                                    onDelete = ForeignKey.CASCADE
                    ),
            ForeignKey(
                            entity = PersonsEntity::class,
                            parentColumns = ["id"],
                            childColumns = ["person_id"],
                            onDelete = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(value = ["category_id"]),
            Index(value = ["person_id"])
        ]
)
data class SoldiersEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val soldierId: Long = 0,

        @ColumnInfo(name = "category_id")
        var categoryId: Long,

        @ColumnInfo(name = "person_id")
        var personId: Long,

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