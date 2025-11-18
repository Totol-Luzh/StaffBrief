package com.bytewave.staffbrief.domain.repository

import android.util.Log
import com.bytewave.staffbrief.data.db.StaffBriefDao
import com.bytewave.staffbrief.data.mappers.toDomain
import com.bytewave.staffbrief.data.mappers.toEntity
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.Relative
import com.bytewave.staffbrief.domain.model.Soldier
import com.bytewave.staffbrief.domain.model.SoldierBrief
import com.bytewave.staffbrief.domain.model.SoldierCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class StaffBriefRepositoryImpl(private val staffBriefDao: StaffBriefDao) : StaffBriefRepository {

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

    override suspend fun insertRelative(relative: Relative): Result<Long> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.insertRelative(relative.toEntity()))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun updateRelative(relative: Relative): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.updateRelative(relative.toEntity()))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun deleteRelatives(soldierId: Long, relativeIds: List<Long>): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            staffBriefDao.deleteRelativesNotInList(soldierId, relativeIds)
            Result.Success(true)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getRelativesBySoldier(soldierId: Long): Result<List<Relative>> = withContext(Dispatchers.IO){
        try {
            Result.Success(staffBriefDao.getRelativesBySoldier(soldierId).map { it.toDomain()})
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

    override suspend fun getCategoriesBySoldier(soldierId: Long): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.getCategoryBySoldier(soldierId).map { it.toDomain() })
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun insertSoldiersCategories(soldiersCategories: List<SoldierCategory>): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            soldiersCategories.forEach { item ->
                if(!staffBriefDao.checkSoldierCategoryExists(item.soldierId, item.categoryId))
                    staffBriefDao.insertSoldierCategory(item.toEntity())
            }
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

    override fun getAllSoldier(categoriesIds: List<Int>, searchQuery: String): Flow<List<SoldierBrief>> {
        return  staffBriefDao.getAllSoldier(categoriesIds, searchQuery)
    }

    override fun getAllSoldierWithoutCategory(searchQuery: String): Flow<List<SoldierBrief>> {
        return  staffBriefDao.getAllSoldierWithoutCategory( searchQuery)
    }

    override fun getAllSoldierWithoutFilter(searchQuery: String): Flow<List<SoldierBrief>> {
        return  staffBriefDao.getAllSoldierWithoutFilter(searchQuery)
    }

    override suspend fun getSoldierById(soldierId: Long): Result<Soldier>  = withContext(
        Dispatchers.IO){
        try {
            Result.Success(staffBriefDao.getSoldierById(soldierId).toDomain())
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

}