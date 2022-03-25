package com.rohan.demodistancecalculator.repositories

import com.rohan.demodistancecalculator.data.network.DistanceNetworkServices
import com.rohan.demodistancecalculator.data.network.LocationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val distanceNetworkServices: DistanceNetworkServices
) {

    suspend fun fetchLocationInfo(lat: Float, lon: Float): Response<LocationResponse> = withContext(
        Dispatchers.IO
    ) {
        val result = distanceNetworkServices.getLocationInfo(lat, lon)
        result
    }
}
