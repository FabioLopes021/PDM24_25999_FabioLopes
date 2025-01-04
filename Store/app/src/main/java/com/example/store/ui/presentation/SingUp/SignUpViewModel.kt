package com.example.store.ui.presentation.SingUp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.data.repository.AuthRepositoryImpl
import com.example.store.domain.model.User
import com.example.store.domain.use_case.AuthUseCase
import com.example.store.presentation.viewModels.AuthState
import com.example.store.utils.showToastMessage
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel: ViewModel() {
    private val repository = AuthRepositoryImpl()
    private val authUseCase = AuthUseCase(repository)

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    suspend fun register(email: String, password: String, context: Context): User?{
        var user: User? = null
        try {
            user = authUseCase.register(email,password)
        }catch (e: Exception){
            _authState.value = AuthState.Error(e.message!!)
        }
        if(user != null){
            showToastMessage(context, "successfully registered!")
            _authState.value = AuthState.Authenticated
            return user
        }else{
            showToastMessage(context, "invalid Email or password!")
            _authState.value = AuthState.Unauthenticated
            return null
        }
    }
}