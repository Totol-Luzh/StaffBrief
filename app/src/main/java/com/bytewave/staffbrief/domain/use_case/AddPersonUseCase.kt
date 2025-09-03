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
        when(val  result = repository.addPerson(person)){
            is Result.Success -> {
                Log.d("Add Person", "Success")
                return result.data
            }
            is Result.Error -> {
                Log.d("Add Person", "Error")
                return -1
            }
        }

    }
}