package com.capstone.moru.ui.routines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.moru.data.db.model.BooksRoutineModel
import com.capstone.moru.data.db.model.ExerciseRoutineModel
import com.capstone.moru.data.repository.UserRepository

class RoutineViewModel(private var userRepository: UserRepository) : ViewModel() {
    lateinit var userExerciseRoutines: LiveData<PagingData<ExerciseRoutineModel>>
    lateinit var userBooksRoutines: LiveData<PagingData<BooksRoutineModel>>

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getAllExercise() {
        userExerciseRoutines = userRepository.getAllExercise("").cachedIn(viewModelScope)
    }

    fun getAllBooks() {
        userBooksRoutines = userRepository.getAllBooks("").cachedIn(viewModelScope)
    }
}