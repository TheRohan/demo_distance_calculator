package com.rohan.demodistancecalculator.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [DistanceInfo::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class DistanceInfoDatabase : RoomDatabase() {

    abstract val dao: DistanceInfoDAO
}