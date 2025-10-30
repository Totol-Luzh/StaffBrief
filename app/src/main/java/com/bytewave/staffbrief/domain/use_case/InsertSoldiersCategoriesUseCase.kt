package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.SoldierCategory
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface InsertSoldiersCategoriesUseCase{
    suspend operator fun invoke(soldierId: Long, list: List<Pair<Category, Boolean>> ): Boolean
}

class InsertSoldiersCategoriesUseCaseImpl(
    private val repository: StaffBriefRepository
) : InsertSoldiersCategoriesUseCase {
    override suspend fun invoke(soldierId: Long, list: List<Pair<Category, Boolean>>): Boolean {

        var listCategoryId: MutableList<Int> = mutableListOf()
        list.forEach {element ->
            if(element.second)
                listCategoryId.add(element.first.id)
        }
        when(val result = repository.deleteRemovedCategories(soldierId, listCategoryId)){
            is Result.Success -> {
                Log.d("Delete SoldierCategory", "Success")
            }
            is Result.Error -> {
                Log.d("Delete SoldierCategory", "Error")
                return false
            }
        }
        when(val result = repository.insertSoldiersCategory(listCategoryId.map{
            SoldierCategory(soldierId = soldierId, categoryId = it)
        })){
            is Result.Success -> {
                Log.d("Insert SoldierCategory", "Success")
                return result.data
            }
            is Result.Error -> {
                Log.d("Insert SoldierCategory", "Error")
                return false
            }
        }
    }
}