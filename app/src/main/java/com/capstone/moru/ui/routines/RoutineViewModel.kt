package com.capstone.moru.ui.routines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.data.api.response.RoutineResponse
import com.capstone.moru.data.db.model.BooksRoutineModel
import com.capstone.moru.data.db.model.ExerciseRoutineModel
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RoutineViewModel(private var userRepository: UserRepository) : ViewModel() {
    lateinit var userExerciseRoutines: LiveData<PagingData<ExerciseRoutineModel>>
    lateinit var userBooksRoutines: LiveData<PagingData<BooksRoutineModel>>

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

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getAllExerciseRoutine() {
        _isLoading.value = true
        val client = userRepository.getAllExerciseRoutine()

        client.enqueue(object : Callback<RoutineResponse> {
            override fun onResponse(
                call: Call<RoutineResponse>,
                response: Response<RoutineResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()

                    _exerciseRoutine.value = response.body()?.list

                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(call: Call<RoutineResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }
        })
    }

    fun getAllBooksRoutine() {
        _isLoading.value = true
        val client = userRepository.getAllBooksRoutine()

        client.enqueue(object : Callback<RoutineResponse> {
            override fun onResponse(
                call: Call<RoutineResponse>,
                response: Response<RoutineResponse>
            ) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()

                    _bookRoutine.value = response.body()?.list

                } else {

                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(call: Call<RoutineResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }
        })
    }

    // *FOR PAGINATION*
    fun getAllExercise() {
        userExerciseRoutines = userRepository.getAllExercise("").cachedIn(viewModelScope)
    }

    fun getAllBooks() {
        userBooksRoutines = userRepository.getAllBooks("").cachedIn(viewModelScope)
    }
    // *FOR PAGINATION*

    companion object {
        private const val TAG = "RoutineViewModel"
    }
}