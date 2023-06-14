package com.capstone.moru.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.moru.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private var userRepository: UserRepository): ViewModel() {
    fun logout(){
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun getUserEmail(): LiveData<String>{
        return userRepository.getUserEmail()
    }
}