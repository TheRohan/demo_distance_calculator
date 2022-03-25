package com.rohan.demodistancecalculator.other

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round
import kotlin.random.Random

object Utility {


    private val listOfLatLng = arrayListOf(
        LatLng(45.41666667, -75.7),
        LatLng(60.16666667, 24.933333),
        LatLng(64.15, -21.95),
        LatLng(28.6, 77.2),
        LatLng(-6.166666667, 106.816667),
        LatLng(-13.96666667, 33.783333),
        LatLng(50.43333333, 30.516667),
        LatLng(15.35, 44.2),
    )

    //NOTE: api dosen't support all world's point
    //this why i hardcoded some capitals
    //Random.nextDouble(-90.0, 90.0),
    //Random.nextDouble(-180.0, 180.0)
    fun createRandomLatLng() = listOfLatLng.random()


    fun getDistanceInM(pos1: LatLng, pos2: LatLng): Int {
        val startPoint = Location("pos1")
        startPoint.latitude = pos1.latitude
        startPoint.longitude = pos1.longitude

        val endPoint = Location("pos2")
        endPoint.latitude = pos2.latitude
        endPoint.longitude = pos2.longitude

        return startPoint.distanceTo(endPoint).toInt()
    }

    @SuppressLint("SimpleDateFormat")
    fun millsToText(createdDate: Long): String {
        return try {
            val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            formatter.format(Date(createdDate))
        } catch (e: Exception) {
            ""
        }
    }

    fun getDistanceInKm(pos1: LatLng, pos2: LatLng): Int = getDistanceInM(pos1, pos2) / 1000

    fun formatFloatToDisplay(old: Float): String = (round(old * 100) / 100).toString()


    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}