package com.bytewave.staffbrief.domain.repository

import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.data.db.entities.PersonsEntity
import com.bytewave.staffbrief.data.db.entities.RelativesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersCategoriesEntity
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.model.SoldierFullInfo
import kotlinx.coroutines.flow.Flow

interface StaffBriefRepository {
    suspend fun addPerson(person: Person): Result<Long>
    suspend fun updatePerson(person: PersonsEntity): Result<Int>
    //suspend fun deletePerson(personId: Long): Result<Int>

    suspend fun addSoldier(soldier: Soldier): Result<Long>
    suspend fun updateSoldier(soldier: Soldier): Result<Int>
    suspend fun deleteSoldier(soldierId: Long): Result<Int>

    suspend fun addRelative(relative: RelativesEntity): Result<Long>
    suspend fun updateRelative(relative: RelativesEntity): Result<Int>
    suspend fun deleteRelative(relativeId: Long): Result<Int>

    suspend fun addCategory(category: CategoriesEntity): Result<Int>
    suspend fun updateCategory(category: CategoriesEntity): Result<Int>
    suspend fun deleteCategory(categoryId: Int): Result<Int>
    fun getAllCategories(): Flow<List<CategoriesEntity>>

    suspend fun addSoldiersCategory(soldiersCategory: SoldiersCategoriesEntity): Result<Long>
    suspend fun deleteSoldierCategory(soldierCategoryId: Long): Result<Int>

    fun getAllSoldierFullInfo(): Flow<List<Person>>
    suspend fun getFullSoldierInfoByPerson(personId: Long): Result<SoldierFullInfo>
}

sealed class Result<out T> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Throwable): Result<Nothing>()
}