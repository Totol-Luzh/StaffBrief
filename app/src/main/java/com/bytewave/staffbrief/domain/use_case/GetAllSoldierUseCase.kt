package com.bytewave.staffbrief.domain.use_case

import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.SoldierBrief
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import kotlinx.coroutines.flow.Flow

interface GetAllSoldierUseCase{
    operator fun invoke(list: List<Pair<Category, Boolean>>, searchString: String): Flow<List<SoldierBrief>>
}

class GetAllSoldierUseCaseImpl(
    private  val repository: StaffBriefRepository
) : GetAllSoldierUseCase {
    override fun invoke(list: List<Pair<Category, Boolean>>, searchQuery: String): Flow<List<SoldierBrief>>{
        var listCategoryId: MutableList<Int> = mutableListOf()
        list.forEach {element ->
            if(element.second)
                listCategoryId.add(element.first.id)
        }
        return if (listCategoryId.isEmpty()) {
            repository.getAllSoldierWithoutFilter(searchQuery)
        } else {
            if (0 in listCategoryId)
                repository.getAllSoldierWithoutCategory(searchQuery)
            else
                repository.getAllSoldier(listCategoryId, searchQuery)
        }
    }

}