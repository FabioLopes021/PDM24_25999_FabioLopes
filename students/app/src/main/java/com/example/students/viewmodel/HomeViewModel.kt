package com.example.students.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.students.model.UserData
import com.example.students.model.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val userRepository: UserRepository = UserRepository()

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    fun getUserData(){
        viewModelScope.launch {
            val userResult = userRepository.fetchUserData()
            _userData.postValue(userResult)
        }
    }
}