package com.capstone.moru.ui.detail.user_routine

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.repository.UserRepository

class DetailRoutineViewModel(private var userRepository: UserRepository) : ViewModel() {

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getUserScheduleDetail(token: String, id: String){
        val formattedToken = "Bearer $token"
        val client = userRepository.getUserScheduleDetail(formattedToken, id)
    }
}