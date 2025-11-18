package com.bytewave.staffbrief.domain.repository

import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.Relative
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.model.SoldierBrief
import com.bytewave.staffbrief.domain.model.SoldierCategory
import kotlinx.coroutines.flow.Flow

interface StaffBriefRepository {

    suspend fun addSoldier(soldier: Soldier): Result<Long>
    suspend fun updateSoldier(soldier: Soldier): Result<Int>
    suspend fun deleteSoldier(soldierId: Long): Result<Int>

    suspend fun insertRelative(relative: Relative): Result<Long>
    suspend fun updateRelative(relative: Relative): Result<Int>
    suspend fun deleteRelatives(soldierId: Long, relativeIds: List<Long>): Result<Boolean>
    suspend fun getRelativesBySoldier(soldierId: Long): Result<List<Relative>>

    suspend fun addCategory(category: Category): Result<Int>
    suspend fun updateCategory(category: Category): Result<Int>
    suspend fun deleteCategory(categoryId: Int): Result<Int>
    fun getAllCategories(): Flow<List<Category>>
    suspend fun getAllCategoriesCurrent(): Result<List<Category>>
    suspend fun getCategoriesBySoldier(soldierId: Long): Result<List<Category>>

    suspend fun insertSoldiersCategories(soldiersCategory: List<SoldierCategory>): Result<Boolean>
    suspend fun deleteRemovedCategories(soldierId: Long, categoryIds: List<Int>): Result<Int>
    suspend fun deleteSoldierCategory(soldierCategoryId: Long): Result<Int>

    fun getAllSoldier(categoriesIds: List<Int>, searchQuery: String): Flow<List<SoldierBrief>>
    fun getAllSoldierWithoutCategory(searchQuery: String): Flow<List<SoldierBrief>>
    fun getAllSoldierWithoutFilter(searchQuery: String): Flow<List<SoldierBrief>>
    suspend fun getSoldierById(soldierId: Long): Result<Soldier>
}

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Throwable): Result<Nothing>()
}