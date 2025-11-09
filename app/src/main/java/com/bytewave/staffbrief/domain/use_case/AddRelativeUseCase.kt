package com.bytewave.staffbrief.domain.use_case

import android.util.Log
import com.bytewave.staffbrief.domain.model.Relative
import com.bytewave.staffbrief.domain.repository.Result
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository

interface AddRelativeUseCase{
    suspend operator fun invoke(soldierId: Long, relatives: List<Relative>): Long
}

class AddRelativeUseCaseImpl(
    private val repository: StaffBriefRepository
) : AddRelativeUseCase {
    override suspend fun invoke(soldierId: Long, relatives: List<Relative>): Long {

        when(val  result = repository.deleteRelatives(soldierId, relatives.map{it.id})){
            is Result.Success -> {
                Log.d("Delete Relatives", "Success")
                result.data
            }
            is Result.Error -> {
                Log.d("Delete Relatives", "Error")
                -1
            }
        }
        relatives.forEach { relative ->
            if(relative.id == 0L){
                when(val  result = repository.insertRelative(relative)){
                    is Result.Success -> {
                        Log.d("Insert Relative", "Success")
                        result.data
                    }
                    is Result.Error -> {
                        Log.d("Insert Relative", "Error")
                        -1
                    }
                }
            } else {
                when(val  result = repository.updateRelative(relative)) {
                    is Result.Success -> {
                        Log.d("Update Relative", "Success")
                        result.data
                    }

                    is Result.Error -> {
                        Log.d("Update Relative", "Error")
                        -1
                    }
                }
            }
        }
        Log.d("Insert Relative", "Nothing insert")
        return -1
    }
}