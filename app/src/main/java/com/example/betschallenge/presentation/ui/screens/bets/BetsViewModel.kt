package com.example.betschallenge.presentation.ui.screens.bets

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.betschallenge.domain.entity.Resource
import com.example.betschallenge.domain.entity.bets.BetItemEntity
import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity
import com.example.betschallenge.domain.usecase.bet_detail.GetBetDetailByFilterUseCase
import com.example.betschallenge.domain.usecase.bets.GetBetsUseCase
import com.example.domain.entity.Failure
import com.example.technicalchallenge.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BetsViewModel @Inject constructor(
    private val getBetsUseCase: GetBetsUseCase,
    private val getBetDetailByFilterUseCase: GetBetDetailByFilterUseCase
) :
    BaseViewModel<Any>() {

    private val _betsState = MutableStateFlow<Resource<List<BetItemEntity>>>(Resource.Idle)
    private val _betDetailState = MutableStateFlow<Resource<List<BetDetailEntity>>>(Resource.Idle)
    val betsState = _betsState.asStateFlow()
    val betDetailState = _betDetailState.asStateFlow()


    private var originalList: List<BetItemEntity> = listOf()
    init {
        getBets()
    }

    fun getBets() {

        viewModelScope.launch {
            _betsState.value = Resource.Loading
            val result = withContext(Dispatchers.IO) {
                getBetsUseCase.invoke()
            }
            result.either(::handleError, ::handleSuccessBet)
        }
    }

    private fun handleError(failure: Failure) {

        _betsState.value = Resource.Error(failure.toString())
    }

    private fun handleSuccessBet(data: List<BetItemEntity>) {
        originalList = data
        _betsState.value = Resource.Success(data)

    }

    fun filterBets(status: String?, type: String?) {
        Log.d("filterBets", "Status: $status, Type: $type")
        Log.d("originalList", "Original List: $originalList")
        val filteredList = originalList.filter { bet ->
            val statusMatch = status.isNullOrEmpty() || bet.status.equals(status, ignoreCase = true)
            val typeMatch = type.isNullOrEmpty() || bet.type.equals(type, ignoreCase = true)
            statusMatch && typeMatch
        }
        Log.d("filterBets", "Filtered List: $filteredList")
        _betsState.value = Resource.Success(filteredList)
    }

    fun getBetDetailByFilter(filter: String) {


        viewModelScope.launch {
            _betDetailState.value = Resource.Loading
            val result = withContext(Dispatchers.IO) {
                getBetDetailByFilterUseCase.invoke(filter)
            }
            result.either(::handleError, ::handleSuccessBetDetail)
        }
    }

    private fun handleSuccessBetDetail(data: List<BetDetailEntity>) {

            _betDetailState.value = Resource.Success(data)

    }

}
