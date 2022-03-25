package com.rohan.demodistancecalculator.data.network

import androidx.annotation.Keep
import com.rohan.demodistancecalculator.data.db.LocationInfo

@Keep
data class LocationResponse(
    val address: Address?,
    val display_name: String?,
    val lat: String?,
    val licence: String?,
    val lon: String?,
    val name: String?
) {
    fun convertToLocationInfo() = LocationInfo(
        lat = lat?.toFloatOrNull() ?: 0f,
        lon = lon?.toFloatOrNull() ?: 0f,
        fullName = display_name,
        name = name,
        country = address?.country,
        state = address?.state,
    )
}

@Keep
data class Address(
    val city: String?,
    val country: String?,
    val state: String?,
)