package com.capstone.moru.ui.add_routine.pick_routine.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.data.repository.UserRepository

class PickRoutineViewModel(private var userRepository: UserRepository) : ViewModel() {
    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _bookRoutine = MutableLiveData<List<ListItem?>?>()
    val bookRoutine: LiveData<List<ListItem?>?> = _bookRoutine

    private val _exerciseRoutine = MutableLiveData<List<ListItem?>?>()
    val exerciseRoutine: LiveData<List<ListItem?>?> = _exerciseRoutine

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error


}