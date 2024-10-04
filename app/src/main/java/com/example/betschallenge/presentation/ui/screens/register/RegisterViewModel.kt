package com.example.betschallenge.presentation.ui.screens.register

import androidx.lifecycle.viewModelScope
import com.example.betschallenge.domain.entity.Resource
import com.example.betschallenge.domain.entity.user.UserEntity
import com.example.betschallenge.domain.usecase.user.CreateUserUseCase
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
class RegisterViewModel@Inject constructor( private val registerUseCase: CreateUserUseCase): BaseViewModel<Any>() {

    private val _registerState = MutableStateFlow<Resource<UserEntity>>(Resource.Idle)
    val registerState = _registerState.asStateFlow()

    fun createUser( correo: String, password: String){
        viewModelScope.launch  {
            _registerState.value = Resource.Loading
                val result =  withContext(Dispatchers.IO) {registerUseCase.invoke(correo, password)}
            result.either(::handleError, ::handleSuccessRegister)
        }
    }
    private fun handleError(failure: Failure) {
        _registerState.value = when (failure) {
            is Failure.ServiceUncaughtFailure -> Resource.Error(failure.uncaughtFailureMessage)
            else -> Resource.Error("Error")
        }
    }
    private fun handleSuccessRegister(userEntity: UserEntity) {
        _registerState.value = Resource.Success(userEntity)
    }

     fun registerEventConsumed() {
        _registerState.value = Resource.Idle
    }
}

