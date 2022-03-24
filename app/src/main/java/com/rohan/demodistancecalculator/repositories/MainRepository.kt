package com.rohan.demodistancecalculator.repositories

import com.rohan.demodistancecalculator.data.db.DistanceInfo
import kotlinx.coroutines.flow.Flow


interface MainRepository {
    suspend fun insertDistanceInfo(run: DistanceInfo)

    suspend fun deleteDistanceInfo(run: DistanceInfo)

    fun filterBy(column : String) : Flow<List<DistanceInfo>>
}