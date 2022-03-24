package com.rohan.demodistancecalculator.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DistanceNetworkServices {

    @GET("reverse")
    suspend fun getLocationInfo(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("format") format: String = "jsonv2",
    ): Response<LocationResponse>
}