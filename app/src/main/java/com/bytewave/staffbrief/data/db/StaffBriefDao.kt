package com.bytewave.staffbrief.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.data.db.entities.RelativesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersCategoriesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersEntity
import com.bytewave.staffbrief.domain.model.SoldierBrief
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffBriefDao {


        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertSoldier(soldier: SoldiersEntity): Long
        @Update
        fun updateSoldier(soldier: SoldiersEntity): Int
        @Query("DELETE FROM soldiers WHERE id = :soldierId")
        fun deleteSoldierById(soldierId: Long): Int


        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertRelative(relative: RelativesEntity): Long
        @Update
        fun updateRelative(relative: RelativesEntity): Int
        @Query("DELETE FROM relatives WHERE soldier_id = :soldierId AND id NOT IN (:relativeIds)")
        fun deleteRelativesNotInList(soldierId: Long, relativeIds: List<Long>)
        @Query("SELECT * FROM relatives WHERE soldier_id = :soldierId")
        fun getRelativesBySoldier(soldierId: Long): List<RelativesEntity>


        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertCategory(category: CategoriesEntity): Long
        @Update
        fun updateCategory(category: CategoriesEntity): Int
        @Query("DELETE FROM categories WHERE id = :categoryId")
        fun deleteCategoryById(categoryId: Int): Int
        @Query("SELECT * FROM categories")
        fun getAllCategories(): Flow<List<CategoriesEntity>>
        @Query("SELECT * FROM categories ORDER BY priority")
        fun getAllCategoriesCurrent(): List<CategoriesEntity>
        @Query("SELECT c.* FROM categories c " +
                "JOIN soldiers_categories sc ON c.id = sc.category_id " +
                "WHERE sc.soldier_id = :soldierId " +
                "ORDER BY c.priority")
        fun getCategoryBySoldier(soldierId: Long): List<CategoriesEntity>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertSoldierCategory(soldierCategory: SoldiersCategoriesEntity)
        @Query("SELECT EXISTS(SELECT 1 FROM soldiers_categories WHERE soldier_id = :soldierId AND category_id = :categoryId)")
        suspend fun checkSoldierCategoryExists(soldierId: Long, categoryId: Int): Boolean
        @Query("DELETE FROM soldiers_categories WHERE soldier_id = :soldierId AND category_id NOT IN (:categoryIds)")
        suspend fun deleteRemovedCategories(soldierId: Long, categoryIds: List<Int>): Int
        @Query("DELETE FROM soldiers_categories WHERE id = :soldierCategoryId")
        fun deleteSoldierCategoryById(soldierCategoryId: Long): Int

        @Query("""
            SELECT DISTINCT 
                s.id AS soldierId,
                s.first_name AS firstName,
                s.last_name AS lastName,
                s.patronymic,
                s.military_rank AS rank,
                s.photo
            FROM soldiers s
            INNER JOIN soldiers_categories sc ON s.id = sc.soldier_id
            WHERE
                (sc.category_id IN (:categoriesIds))
                AND (
                    :searchString IS NULL
                    OR s.first_name LIKE '%' || :searchString || '%'
                    OR s.last_name LIKE '%' || :searchString || '%'
                    OR s.patronymic LIKE '%' || :searchString || '%'
                    OR s.info LIKE '%' || :searchString || '%'
                )
            ORDER BY s.last_name, s.first_name
        """)
        fun getAllSoldier(categoriesIds: List<Int>, searchString: String): Flow<List<SoldierBrief>>

        @Query("""
            SELECT 
                s.id AS soldierId,
                s.first_name AS firstName,
                s.last_name AS lastName,
                s.patronymic,
                s.military_rank AS rank,
                s.photo
            FROM soldiers s
            LEFT JOIN soldiers_categories sc ON s.id = sc.soldier_id
            WHERE sc.soldier_id IS NULL
                AND (
                    :searchString IS NULL
                    OR s.first_name LIKE '%' || :searchString || '%'
                    OR s.last_name LIKE '%' || :searchString || '%'
                    OR s.patronymic LIKE '%' || :searchString || '%'
                    OR s.info LIKE '%' || :searchString || '%'
                )
            ORDER BY s.last_name, s.first_name;
        """)
        fun getAllSoldierWithoutCategory(searchString: String?): Flow<List<SoldierBrief>>

        @Query("""
            SELECT DISTINCT
                id AS soldierId,
                first_name AS firstName,
                last_name AS lastName,
                patronymic,
                military_rank AS rank,
                photo
            FROM soldiers
            WHERE (
                :searchString IS NULL 
                OR first_name LIKE '%' || :searchString || '%'
                OR last_name LIKE '%' || :searchString || '%'
                OR patronymic LIKE '%' || :searchString || '%'
                OR info LIKE '%' || :searchString || '%'
            )
            ORDER BY last_name, first_name
        """)
        fun getAllSoldierWithoutFilter(searchString: String?): Flow<List<SoldierBrief>>
        @Query("""
        SELECT * FROM soldiers
        WHERE id = :soldierId
        """)
        suspend fun getSoldierById(soldierId: Long): SoldiersEntity


}