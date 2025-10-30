package com.bytewave.staffbrief.domain.use_case

import com.bytewave.staffbrief.domain.model.Category
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import kotlinx.coroutines.flow.Flow

interface GetAllPersonBySoldierUseCase{
    operator fun invoke(list: List<Pair<Category, Boolean>>, searchString: String): Flow<List<Person>>
}

class GetAllSoldierFullInfoUseCaseImpl(
    private  val repository: StaffBriefRepository
) : GetAllPersonBySoldierUseCase {
    override fun invoke(list: List<Pair<Category, Boolean>>, searchQuery: String): Flow<List<Person>>{
        var listCategoryId: MutableList<Int> = mutableListOf()
        list.forEach {element ->
            if(element.second)
                listCategoryId.add(element.first.id)
        }
        return if (listCategoryId.isEmpty()) {
            repository.getAllSoldierWithoutFilter(searchQuery)
        } else {
            repository.getAllSoldierFullInfo(listCategoryId, searchQuery)
        }
    }

}