package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.model.Person
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface AddPersonUseCase{
    suspend operator fun invoke(person: Person): Long
}

class AddPersonUseCaseImpl(
    private val repository: StaffBriefRepository
) : AddPersonUseCase {
    override suspend fun invoke(person: Person): Long {
        if(person.id == 0L){
            return when(val  result = repository.insertPerson(person)){
                is Result.Success -> {
                    Log.d("Add Person", "Success")
                    result.data
                }
                is Result.Error -> {
                    Log.d("Add Person", "Error")
                    -1
                }
            }
        } else {
            return when(val  result = repository.updatePerson(person)){
                is Result.Success -> {
                    Log.d("Add Person", "Success")
                    result.data.toLong()
                }
                is Result.Error -> {
                    Log.d("Add Person", "Error")
                    -1
                }
            }
        }

    }
}