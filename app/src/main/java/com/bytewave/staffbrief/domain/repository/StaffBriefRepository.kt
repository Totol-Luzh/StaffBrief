package com.bytewave.staffbrief.domain.repository

import com.bytewave.staffbrief.data.db.entities.PersonsEntity
import com.bytewave.staffbrief.data.db.entities.RelativesEntity
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.model.SoldierCategory
import com.bytewave.staffbrief.domain.model.SoldierFullInfo
import kotlinx.coroutines.flow.Flow

interface StaffBriefRepository {
    suspend fun addPerson(person: Person): Result<Long>
    suspend fun updatePerson(person: Person): Result<Int>
    //suspend fun deletePerson(personId: Long): Result<Int>

    suspend fun addSoldier(soldier: Soldier): Result<Long>
    suspend fun updateSoldier(soldier: Soldier): Result<Int>
    suspend fun deleteSoldier(soldierId: Long): Result<Int>

    suspend fun addRelative(relative: RelativesEntity): Result<Long>
    suspend fun updateRelative(relative: RelativesEntity): Result<Int>
    suspend fun deleteRelative(relativeId: Long): Result<Int>

    suspend fun addCategory(category: Category): Result<Int>
    suspend fun updateCategory(category: Category): Result<Int>
    suspend fun deleteCategory(categoryId: Int): Result<Int>
    fun getAllCategories(): Flow<List<Category>>
    suspend fun getAllCategoriesCurrent(): Result<List<Category>>

    suspend fun insertSoldiersCategory(soldiersCategory: List<SoldierCategory>): Result<Boolean>
    suspend fun deleteRemovedCategories(soldierId: Long, categoryIds: List<Int>): Result<Int>
    suspend fun deleteSoldierCategory(soldierCategoryId: Long): Result<Int>

    fun getAllSoldierFullInfo(categoriesIds: List<Int>, searchQuery: String): Flow<List<Person>>
    fun getAllSoldierWithoutFilter(searchQuery: String): Flow<List<Person>>
    suspend fun getFullSoldierInfoByPerson(personId: Long): Result<SoldierFullInfo>
}

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Throwable): Result<Nothing>()
}