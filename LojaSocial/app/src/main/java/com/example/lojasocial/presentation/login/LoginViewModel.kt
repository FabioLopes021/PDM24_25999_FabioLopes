package com.example.lojasocial.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojasocial.data.repository.AuthRepositoryImpl
import com.example.lojasocial.domain.model.User
import com.example.lojasocial.domain.use_case.AuthUseCase
import com.example.lojasocial.utils.AuthState
import com.example.lojasocial.utils.showToastMessage
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val repository = AuthRepositoryImpl(auth)
    private val authUseCase = AuthUseCase(repository)

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState>  = _authState

    init {
        _authState.value = AuthState.Unauthenticated
    }

    suspend fun login(email: String, password: String, context: Context,): User? {
        var user: User? = null
        try {
            user = authUseCase.login(email,password)
        }catch (e: Exception){
            _authState.value = AuthState.Error(e.message!!)
        }
        if(user != null){
            showToastMessage(context, "successfully logged in!")
            _authState.value = AuthState.Authenticated
            return user
        }else{
            showToastMessage(context, "Wrong Email or password!")
            _authState.value = AuthState.Unauthenticated
            return null
        }
    }
}