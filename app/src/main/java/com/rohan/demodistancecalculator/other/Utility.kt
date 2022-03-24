package com.rohan.demodistancecalculator.other

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlin.math.round
import kotlin.random.Random

object Utility {
    fun createRandomLatLng() = LatLng(
        Random.nextDouble(-90.0, 90.0),
        Random.nextDouble(-180.0, 180.0)
    )

    fun getDistanceInM(pos1: LatLng, pos2: LatLng): Int {
        val startPoint = Location("pos1")
        startPoint.latitude = pos1.latitude
        startPoint.longitude = pos1.longitude

        val endPoint = Location("pos2")
        endPoint.latitude = pos2.latitude
        endPoint.longitude = pos2.longitude

        return startPoint.distanceTo(endPoint).toInt()
    }

    fun getDistanceInKm(pos1: LatLng, pos2: LatLng): Int = getDistanceInM(pos1, pos2) / 1000

    fun formatFloatToDisplay(old: Float): String = (round(old * 100) / 100).toString()

}