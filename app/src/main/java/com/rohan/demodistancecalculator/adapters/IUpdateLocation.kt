package com.rohan.demodistancecalculator.adapters

import com.google.android.gms.maps.model.LatLng

interface IUpdateLocation {
    fun updateLatLng(pos: LatLng)
}