package com.bytewave.staffbrief.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "relatives",
        indices = [Index(value = ["soldier_id"])],
        foreignKeys = [
                    ForeignKey(
                                    entity = SoldiersEntity::class,
                                    parentColumns =["id"],
                                    childColumns = ["soldier_id"],
                                    onDelete = ForeignKey.CASCADE
),
            ForeignKey(
                            entity = PersonsEntity::class,
                            parentColumns = ["id"],
                            childColumns = ["person_id"],
                            onDelete = ForeignKey.CASCADE
)
        ]
)
data class RelativesEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val relativeId: Long = 0,

        @ColumnInfo(name = "soldier_id")
        var soldierId: Long,

        @ColumnInfo(name = "person_id")
        var personId: Long,

        @ColumnInfo(name = "kinship")
        var kinship: String? = null
)