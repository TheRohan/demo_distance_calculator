package com.rohan.demodistancecalculator.data.db

import androidx.room.*
import com.rohan.demodistancecalculator.other.Constants.DISTANCE_SORT_KEY
import com.rohan.demodistancecalculator.other.Constants.TIME_CREATE_SORT_KEY
import kotlinx.coroutines.flow.Flow

@Dao
interface DistanceInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDistanceInfo(distanceInfo: DistanceInfo)

    @Delete
    suspend fun deleteDistanceInfo(distanceInfo: DistanceInfo)

    @Query(
        """
        SELECT * FROM distance_info_table
        ORDER BY
        CASE WHEN :column = '$TIME_CREATE_SORT_KEY'  THEN createdDate END DESC,
        CASE WHEN :column = '$DISTANCE_SORT_KEY' THEN distanceInM END DESC
    """
    )
    fun filterBy(column: String): Flow<List<DistanceInfo>>
}