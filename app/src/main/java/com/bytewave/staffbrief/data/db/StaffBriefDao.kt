package com.bytewave.staffbrief.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.data.db.entities.PersonsEntity
import com.bytewave.staffbrief.data.db.entities.RelativesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersCategoriesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersEntity

@Dao
interface StaffBriefDao {

        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertPerson(person: PersonsEntity): Long
        @Update
        fun updatePerson(person: PersonsEntity): Int
        @Query("DELETE FROM soldiers WHERE id = :personId")
        fun deletePersonById(personId: Long)

        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertSoldier(soldier: SoldiersEntity): Long
        @Update
        fun updateSoldier(soldier: SoldiersEntity): Int
        @Query("DELETE FROM soldiers WHERE id = :soldierId")
        fun deleteSoldierById(soldierId: Long)


        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertRelative(relative: RelativesEntity): Long
        @Update
        fun updateRelative(relative: RelativesEntity): Int
        @Query("DELETE FROM relatives WHERE id = :relativeId")
        fun deleteRelativeById(relativeId: Long)


        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertCategory(category: CategoriesEntity): Long
        @Update
        fun updateCategory(category: CategoriesEntity): Int
        @Query("DELETE FROM categories WHERE id = :categoryId")
        fun deleteCategoryById(categoryId: Long)


        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertSoldiersCategories(soldCat: SoldiersCategoriesEntity): Long
        @Query("DELETE FROM soldiers_categories WHERE id = :soldierCategoryId")
        fun deleteSoldierCategoryById(soldierCategoryId: Long)

}