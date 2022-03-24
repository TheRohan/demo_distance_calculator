package com.rohan.demodistancecalculator.repositories

import com.rohan.demodistancecalculator.data.db.DistanceInfo
import com.rohan.demodistancecalculator.data.db.DistanceInfoDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(
    val DistanceInfoDAO: DistanceInfoDAO
) : MainRepository {

    override suspend fun insertDistanceInfo(distanceInfo: DistanceInfo) {
        DistanceInfoDAO.insertDistanceInfo(distanceInfo)
    }

    override suspend fun deleteDistanceInfo(distanceInfo: DistanceInfo) {
        DistanceInfoDAO.deleteDistanceInfo(distanceInfo)
    }

    override fun filterBy(column: String): Flow<List<DistanceInfo>> {
        return DistanceInfoDAO.filterBy(column)
    }
}