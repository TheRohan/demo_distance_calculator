package com.rohan.demodistancecalculator.data.db

import android.graphics.Bitmap
import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "distance_info_table")
data class DistanceInfo(
    var img: Bitmap? = null,

    @Embedded(prefix = "location_info_1")
    var locationInfo1: LocationInfo,

    @Embedded(prefix = "location_info_2")
    var locationInfo2: LocationInfo,

    var distanceInM: Int = 0,
    var distanceInKM: Int = 0,
    var createdDate: Long = 0L,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
