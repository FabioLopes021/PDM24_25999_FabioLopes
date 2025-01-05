package com.example.store.ui.presentation.SignUp

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.data.repository.AuthRepositoryImpl
import com.example.store.data.repository.UtilizadorRepositoryImpl
import com.example.store.domain.model.User
import com.example.store.domain.model.Utilizador
import com.example.store.domain.use_case.AuthUseCase
import com.example.store.domain.use_case.UtilizadorUseCase
import com.example.store.presentation.viewModels.AuthState
import com.example.store.utils.showToastMessage

class SignUpViewModel: ViewModel() {
    //Auth implementation
    private val repository = AuthRepositoryImpl()
    private val authUseCase = AuthUseCase(repository)
    //User implementation
    private val userRepository = UtilizadorRepositoryImpl()
    private val userUseCase = UtilizadorUseCase(userRepository)


    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    var password =  mutableStateOf("")
    var email =  mutableStateOf("")
    var morada =  mutableStateOf("")
    var nome =  mutableStateOf("")


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

    suspend fun CriarUtilizador(email: String, morada: String, nome: String){
        val utilizador: Utilizador = Utilizador("",email,morada,nome,false)
        if(!userUseCase.AdicionarUtilizador(utilizador))
            _authState.value = AuthState.Error("Erro ao registar utilizador!")
    }

}