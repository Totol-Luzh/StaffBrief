package com.bytewave.staffbrief.domain.repository

import android.util.Log
import com.bytewave.staffbrief.data.db.StaffBriefDao
import com.bytewave.staffbrief.data.db.entities.PersonsEntity
import com.bytewave.staffbrief.data.db.entities.RelativesEntity
import com.bytewave.staffbrief.data.mappers.toDomain
import com.bytewave.staffbrief.data.mappers.toEntity
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.model.SoldierCategory
import com.bytewave.staffbrief.domain.model.SoldierFullInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class StaffBriefRepositoryImpl(private val staffBriefDao: StaffBriefDao) : StaffBriefRepository {

    override suspend fun addPerson(person: Person): Result<Long> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.insertPerson(person.toEntity()))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun updatePerson(person: Person): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.updatePerson(person.toEntity()))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

//    override suspend fun deletePerson(personId: Long): Result<Int> = withContext(Dispatchers.IO) {
//        try {
//            Result.Success(staffBriefDao.deletePersonById(personId))
//        } catch (e: Throwable) {
//            Result.Error(e)
//        }
//    }

    override suspend fun addSoldier(soldier: Soldier): Result<Long> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.insertSoldier(soldier.toEntity()))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun updateSoldier(soldier: Soldier): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.updateSoldier(soldier.toEntity()))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun deleteSoldier(soldierId: Long): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.deleteSoldierById(soldierId))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun addRelative(relative: RelativesEntity): Result<Long> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.insertRelative(relative))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun updateRelative(relative: RelativesEntity): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.updateRelative(relative))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun deleteRelative(relativeId: Long): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.deleteRelativeById(relativeId))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun addCategory(category: Category): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.insertCategory(category.toEntity()).toInt())
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun updateCategory(category: Category): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.updateCategory(category.toEntity()))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun deleteCategory(categoryId: Int): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.deleteCategoryById(categoryId))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getAllCategoriesCurrent(): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.getAllCategoriesCurrent().map{it.toDomain()})
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return staffBriefDao.getAllCategories()
            .map { entities ->
                entities.map { it.toDomain()
                }
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun insertSoldiersCategory(soldiersCategory: List<SoldierCategory>): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            Log.d("Insert Category Soldier", "$soldiersCategory")
            staffBriefDao.insertSoldiersCategories(soldiersCategory.map{
                it.toEntity()
            })
            Result.Success(true)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun deleteRemovedCategories(soldierId: Long, categoryIds: List<Int>): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.deleteRemovedCategories(soldierId, categoryIds))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun deleteSoldierCategory(soldierCategoryId: Long): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.deleteSoldierCategoryById(soldierCategoryId))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override fun getAllSoldierFullInfo(categoriesIds: List<Int>, searchQuery: String): Flow<List<Person>> {
        return  staffBriefDao.getAllPersonBySoldier(categoriesIds, searchQuery).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getAllSoldierWithoutFilter(searchQuery: String): Flow<List<Person>> {
        return  staffBriefDao.getAllPersonsBySoldierWithoutFilter(searchQuery).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getFullSoldierInfoByPerson(personId: Long): Result<SoldierFullInfo>  = withContext(
        Dispatchers.IO){
        try {
            Result.Success(staffBriefDao.getFullSoldierInfoByPerson(personId))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

}