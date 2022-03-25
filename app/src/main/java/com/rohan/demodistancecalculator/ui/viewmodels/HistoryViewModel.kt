package com.rohan.demodistancecalculator.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.rohan.demodistancecalculator.other.Constants.DISTANCE_SORT_KEY
import com.rohan.demodistancecalculator.other.Constants.TIME_CREATE_SORT_KEY
import com.rohan.demodistancecalculator.other.SortType
import com.rohan.demodistancecalculator.repositories.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    dbRepository: DBRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.DATE)
    val sortType = _sortType.asStateFlow()


    @ExperimentalCoroutinesApi
    val distanceInfoList = sortType.flatMapLatest {
        when (it) {
            SortType.DATE -> dbRepository.filterBy(TIME_CREATE_SORT_KEY)
            SortType.DISTANCE -> dbRepository.filterBy(DISTANCE_SORT_KEY)
        }
    }

    fun sortDistanceInfoList(sortType: SortType) {
        this._sortType.value = sortType
    }
}