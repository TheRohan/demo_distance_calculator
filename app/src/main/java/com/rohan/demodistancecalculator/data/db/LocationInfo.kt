package com.rohan.demodistancecalculator.data.db

import androidx.annotation.Keep

@Keep
data class LocationInfo(
    var lat: Float = 0f,
    var lon: Float = 0f,
    var fullName: String? = null,
    var name: String? = null,
    var country: String? = null,
    var state: String? = null,
)