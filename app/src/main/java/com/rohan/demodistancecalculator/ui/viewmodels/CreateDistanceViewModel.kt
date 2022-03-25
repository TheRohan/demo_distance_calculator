package com.rohan.demodistancecalculator.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.rohan.demodistancecalculator.data.db.DistanceInfo
import com.rohan.demodistancecalculator.data.network.LocationResponse
import com.rohan.demodistancecalculator.other.Resource
import com.rohan.demodistancecalculator.repositories.DBRepository
import com.rohan.demodistancecalculator.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateDistanceViewModel @Inject constructor(
    private val dbRepository: DBRepository,
    private val networkRepository: NetworkRepository,
) : ViewModel() {


    private val _startPoint = MutableStateFlow<Resource<LocationResponse?>>(Resource.Success(null))
    val startPoint = _startPoint.asStateFlow()

    private val _endPoint = MutableStateFlow<Resource<LocationResponse?>>(Resource.Success(null))
    val endPoint = _endPoint.asStateFlow()

    fun setNewStartPoint(newVal: LatLng) = viewModelScope.launch {
        _startPoint.emit(Resource.Loading())

        try {
            val result = networkRepository.fetchLocationInfo(
                newVal.latitude.toFloat(),
                newVal.longitude.toFloat()
            )
            if (result.isSuccessful && result.body() != null) {
                _startPoint.emit(Resource.Success(result.body()))
            } else {
                _startPoint.emit(Resource.Error(result.message()))
            }
        } catch (e: Exception) {
            _startPoint.emit(Resource.Error(e.message ?: ""))
        }


    }

    fun setNewEndPoint(newVal: LatLng) = viewModelScope.launch {
        _endPoint.emit(Resource.Loading())
        try {
            val result = networkRepository.fetchLocationInfo(
                newVal.latitude.toFloat(),
                newVal.longitude.toFloat()
            )
            if (result.isSuccessful && result.body() != null) {
                _endPoint.emit(Resource.Success(result.body()))
            } else {
                _endPoint.emit(Resource.Error(result.message()))
            }
        } catch (e: Exception) {
            _endPoint.emit(Resource.Error(e.message ?: ""))
        }
    }

    fun createDistanceInfo(distanceInfo: DistanceInfo) = viewModelScope.launch {
        dbRepository.insertDistanceInfo(distanceInfo)
    }

    fun isValidCoordinates(): Boolean {
        when (_startPoint.value) {
            is Resource.Success -> {
                if (_startPoint.value.data == null) {
                    return false
                }
                if (_startPoint.value.data?.lat == null) {
                    return false
                }
                if (_startPoint.value.data?.lon == null) {
                    return false
                }
            }
            is Resource.Error -> return false
            is Resource.Loading -> return false
        }

        when (_endPoint.value) {
            is Resource.Success -> {
                if (_endPoint.value.data == null) {
                    return false
                }
                if (_endPoint.value.data?.lat == null) {
                    return false
                }
                if (_endPoint.value.data?.lon == null) {
                    return false
                }
            }
            is Resource.Error -> return false
            is Resource.Loading -> return false
        }

        return true
    }
}