package com.rohan.demodistancecalculator.repositories

import com.rohan.demodistancecalculator.data.db.DistanceInfo
import kotlinx.coroutines.flow.Flow


interface DBRepository {
    suspend fun insertDistanceInfo(distanceInfo: DistanceInfo)

    suspend fun deleteDistanceInfo(distanceInfo: DistanceInfo)

    fun filterBy(column : String) : Flow<List<DistanceInfo>>

}