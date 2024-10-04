package com.example.betschallenge.presentation.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.example.betschallenge.domain.entity.Resource
import com.example.betschallenge.domain.entity.user.UserEntity
import com.example.betschallenge.domain.usecase.user.LoginUseCase
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
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    BaseViewModel<Any>() {

    private val _loginState = MutableStateFlow<Resource<UserEntity>>(Resource.Idle)
    val loginState = _loginState.asStateFlow()

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading
            val result = withContext(Dispatchers.IO) {
                loginUseCase.invoke(correo, password)
            }
            result.either(::handleError, ::handleSuccessLogin)
        }
    }

    private fun handleError(failure: Failure) {
        _loginState.value = when (failure) {
            is Failure.ServiceUncaughtFailure -> Resource.Error(failure.uncaughtFailureMessage)
            else -> Resource.Error("Error")
        }
    }

    private fun handleSuccessLogin(data: UserEntity) {
        _loginState.value = Resource.Success(data)
    }

    fun loginEventConsumed() {
        _loginState.value = Resource.Idle
    }
}