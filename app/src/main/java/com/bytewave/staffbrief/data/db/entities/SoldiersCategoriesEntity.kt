package com.bytewave.staffbrief.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "soldiers_categories", indices = [Index(value = ["soldier_id"]), Index(value = ["category_id"])],
        foreignKeys = [
                ForeignKey(
                            entity = SoldiersEntity::class,
                            parentColumns = ["id"],
                            childColumns = ["soldier_id"],
                            onDelete = ForeignKey.CASCADE
                ),
        ForeignKey(
                    entity = CategoriesEntity::class,
                    parentColumns = ["id"],
                    childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )])
data class SoldiersCategoriesEntity(
        @PrimaryKey (autoGenerate = true)
        @ColumnInfo(name = "id")
        var soldierCategoryId: Long,
        @ColumnInfo(name = "soldier_id")
        var soldierId: Long,
        @ColumnInfo(name = "category_id")
        var categoryId: Long
)