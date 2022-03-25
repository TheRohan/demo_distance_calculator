package com.rohan.demodistancecalculator.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.demodistancecalculator.data.db.DistanceInfo
import com.rohan.demodistancecalculator.repositories.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DistanceInfoViewModel @Inject constructor(
    private val dbRepository: DBRepository
) : ViewModel() {

    private val _distanceInfo = MutableStateFlow<DistanceInfo?>(null)
    val distanceInfo = _distanceInfo.asStateFlow()

    fun loadDistanceInfoById(id: Int) = viewModelScope.launch {
        val result = dbRepository.getById(id)
        _distanceInfo.value = result.firstOrNull()
    }

}
