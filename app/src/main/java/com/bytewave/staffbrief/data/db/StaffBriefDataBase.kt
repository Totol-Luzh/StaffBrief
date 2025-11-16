package com.bytewave.staffbrief.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bytewave.staffbrief.data.db.converters.BitmapConverter
import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.data.db.entities.RelativesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersCategoriesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersEntity


const val DATABASE_NAME = "staff_brief_room_database"

@Database(
    entities = [ SoldiersEntity::class, RelativesEntity::class, CategoriesEntity::class, SoldiersCategoriesEntity::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(BitmapConverter::class)
abstract class StaffBriefDataBase: RoomDatabase() {
    abstract val staffBriefDao: StaffBriefDao

    companion object {
        @Volatile
        private var INSTANCE: StaffBriefDataBase? = null
        fun getInstance(context: Context): StaffBriefDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StaffBriefDataBase::class.java,
                        DATABASE_NAME
                    )

                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}