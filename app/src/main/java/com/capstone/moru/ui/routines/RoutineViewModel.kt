package com.capstone.moru.ui.routines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.moru.data.api.response.*
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

    private val _bookRoutine = MutableLiveData<List<BookListItem?>?>()
    val bookRoutine: LiveData<List<BookListItem?>?> = _bookRoutine

    private val _exerciseRoutine = MutableLiveData<List<ExerciseListItem?>?>()
    val exerciseRoutine: LiveData<List<ExerciseListItem?>?> = _exerciseRoutine

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getAllExerciseRoutine(token: String) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getAllExerciseRoutine(formatToken)

        client.enqueue(object : Callback<ExerciseListResponse> {
            override fun onResponse(
                call: Call<ExerciseListResponse>,
                response: Response<ExerciseListResponse>
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

            override fun onFailure(call: Call<ExerciseListResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"

                Log.e("RoutineViewModel", _message.value.toString())
            }
        })
    }

    fun getAllBooksRoutine(token: String) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getAllBooksRoutine(formatToken)

        client.enqueue(object : Callback<BookListResponse> {
            override fun onResponse(
                call: Call<BookListResponse>,
                response: Response<BookListResponse>
            ) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()

                    _bookRoutine.value = response.body()?.list

                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"

                    Log.e("RoutineViewModel", _message.value.toString())

                }
            }

            override fun onFailure(call: Call<BookListResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"

                Log.e("RoutineViewModel", _message.value.toString())

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