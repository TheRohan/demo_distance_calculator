package com.rohan.demodistancecalculator.data.network

import com.rohan.demodistancecalculator.data.db.LocationInfo

data class LocationResponse(
    val address: Address?,
    val addresstype: String?,
    val boundingbox: List<String>?,
    val category: String?,
    val display_name: String?,
    val importance: Int?,
    val lat: String?,
    val licence: String?,
    val lon: String?,
    val name: String?,
    val osm_id: Long?,
    val osm_type: String?,
    val place_id: Int?,
    val place_rank: Int?,
    val type: String?
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

data class Address(
    val city: String?,
    val country: String?,
    val country_code: String?,
    val highway: String?,
    val postcode: String?,
    val road: String?,
    val state: String?,
    val state_district: String?,
    val town: String?
)