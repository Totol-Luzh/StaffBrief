package com.bytewave.staffbrief.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "categories", indices = [Index(value = ["name"], unique = true)])

data class CategoriesEntity(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    val categoryId: Int,
    val name: String,
    val priority: Byte,
    val color: Int
)
