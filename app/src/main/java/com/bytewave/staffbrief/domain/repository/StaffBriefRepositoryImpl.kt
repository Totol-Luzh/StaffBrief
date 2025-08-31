package com.bytewave.staffbrief.domain.repository

import com.bytewave.staffbrief.data.db.StaffBriefDao
import com.bytewave.staffbrief.data.db.entities.CategoriesEntity
import com.bytewave.staffbrief.data.db.entities.RelativesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersCategoriesEntity
import com.bytewave.staffbrief.data.db.entities.SoldiersEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class StaffBriefRepositoryImpl(private val staffBriefDao: StaffBriefDao) : StaffBriefRepository {

    override suspend fun addSoldier(soldier: SoldiersEntity): Result<Long> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.insertSoldier(soldier))
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun updateSoldier(soldier: SoldiersEntity): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.updateSoldier(soldier))
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

    override suspend fun addCategory(category: CategoriesEntity): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.insertCategory(category).toInt())
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun updateCategory(category: CategoriesEntity): Result<Int> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.updateCategory(category))
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

    override fun getAllCategories(): Flow<List<CategoriesEntity>> {
        return staffBriefDao.getAllCategories()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun addSoldiersCategory(soldiersCategory: SoldiersCategoriesEntity): Result<Long> = withContext(Dispatchers.IO) {
        try {
            Result.Success(staffBriefDao.insertSoldiersCategories(soldiersCategory))
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


}