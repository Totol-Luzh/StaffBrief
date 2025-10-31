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
import com.bytewave.staffbrief.domain.model.SoldierFullInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffBriefDao {

        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertPerson(person: PersonsEntity): Long
        @Update
        fun updatePerson(person: PersonsEntity): Int
        @Query("DELETE FROM persons WHERE id = :personId")
        fun deletePersonById(personId: Long)

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
        @Query("DELETE FROM relatives WHERE id = :relativeId")
        fun deleteRelativeById(relativeId: Long): Int


        @Insert(onConflict = OnConflictStrategy.ABORT)
        fun insertCategory(category: CategoriesEntity): Long
        @Update
        fun updateCategory(category: CategoriesEntity): Int
        @Query("DELETE FROM categories WHERE id = :categoryId")
        fun deleteCategoryById(categoryId: Int): Int
        @Query("SELECT * FROM categories")
        fun getAllCategories(): Flow<List<CategoriesEntity>>
        @Query("SELECT * FROM categories")
        fun getAllCategoriesCurrent(): List<CategoriesEntity>
        @Query("SELECT c.* FROM categories c " +
                "JOIN soldiers_categories sc ON c.id = sc.category_id " +
                "WHERE sc.soldier_id = :soldierId")
        fun getCategoryBySoldier(soldierId: Long): List<CategoriesEntity>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insertSoldiersCategories(soldierCategory: List<SoldiersCategoriesEntity>)
        @Query("DELETE FROM soldiers_categories WHERE soldier_id = :soldierId AND category_id NOT IN (:categoryIds)")
        suspend fun deleteRemovedCategories(soldierId: Long, categoryIds: List<Int>): Int
        @Query("DELETE FROM soldiers_categories WHERE id = :soldierCategoryId")
        fun deleteSoldierCategoryById(soldierCategoryId: Long): Int

        @Query("""
            SELECT DISTINCT p.*
            FROM persons p
            INNER JOIN soldiers s ON p.id = s.person_id
            INNER JOIN soldiers_categories sc ON s.id = sc.soldier_id
            WHERE 
                (sc.category_id IN (:categoriesIds))
                AND (
                    :searchString IS NULL 
                    OR p.first_name LIKE '%' || :searchString || '%'
                    OR p.last_name LIKE '%' || :searchString || '%'
                    OR p.patronymic LIKE '%' || :searchString || '%'
                    OR s.info LIKE '%' || :searchString || '%'
                )
            ORDER BY p.last_name, p.first_name
        """)
        fun getAllPersonBySoldier(categoriesIds: List<Int>, searchString: String): Flow<List<PersonsEntity>>
        @Query("""
            SELECT DISTINCT p.*
            FROM persons p
            INNER JOIN soldiers s ON p.id = s.person_id
            WHERE (
                :searchString IS NULL 
                OR p.first_name LIKE '%' || :searchString || '%'
                OR p.last_name LIKE '%' || :searchString || '%'
                OR p.patronymic LIKE '%' || :searchString || '%'
                OR s.info LIKE '%' || :searchString || '%'
            )
            ORDER BY p.last_name, p.first_name
        """)
        fun getAllPersonsBySoldierWithoutFilter(searchString: String?): Flow<List<PersonsEntity>>
        @Query("""
        SELECT 
            s.id AS soldierId,
            s.person_id AS personId,
            p.first_name AS firstName,
            p.last_name AS lastName,
            p.patronymic AS patronymic,
            p.birth_date AS birthDate,
            p.phone_number AS phoneNumber,
            s.military_rank AS rank,
            s.info AS info,
            s.positive AS positive,
            s.negative AS negative
        FROM soldiers s
        INNER JOIN persons p ON s.person_id = p.id
        WHERE p.id = :personId
        """)
        suspend fun getFullSoldierInfoByPerson(personId: Long): SoldierFullInfo


}