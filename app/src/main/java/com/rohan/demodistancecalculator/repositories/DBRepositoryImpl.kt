package com.rohan.demodistancecalculator.repositories

import com.rohan.demodistancecalculator.data.db.DistanceInfo
import com.rohan.demodistancecalculator.data.db.DistanceInfoDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DBRepositoryImpl @Inject constructor(
    val distanceInfoDAO: DistanceInfoDAO
) : DBRepository {

    override suspend fun insertDistanceInfo(distanceInfo: DistanceInfo) {
        distanceInfoDAO.insertDistanceInfo(distanceInfo)
    }

    override suspend fun deleteDistanceInfo(distanceInfo: DistanceInfo) {
        distanceInfoDAO.deleteDistanceInfo(distanceInfo)
    }

    override fun filterBy(column: String): Flow<List<DistanceInfo>> {
        return distanceInfoDAO.filterBy(column)
    }
}