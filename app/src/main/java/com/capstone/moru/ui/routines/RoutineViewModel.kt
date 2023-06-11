package com.capstone.moru.ui.routines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.moru.data.api.response.BookListResponse
import com.capstone.moru.data.api.response.ExerciseListResponse
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
    var message: LiveData<String> = _message

    private var _bookRoutine =
        MutableLiveData<List<com.capstone.moru.data.api.response.BookListItem?>?>()
    var bookRoutine: LiveData<List<com.capstone.moru.data.api.response.BookListItem?>?> =
        _bookRoutine

    private var _exerciseRoutine =
        MutableLiveData<List<com.capstone.moru.data.api.response.ExerciseListItem?>?>()
    var exerciseRoutine: LiveData<List<com.capstone.moru.data.api.response.ExerciseListItem?>?> =
        _exerciseRoutine

    private var _searchBookRoutine =
        MutableLiveData<List<com.capstone.moru.data.api.response.BookListItem?>?>()
    var searchBookRoutine: LiveData<List<com.capstone.moru.data.api.response.BookListItem?>?> =
        _searchBookRoutine

    private var _searchExerciseRoutine =
        MutableLiveData<List<com.capstone.moru.data.api.response.ExerciseListItem?>?>()
    var searchExerciseRoutine: LiveData<List<com.capstone.moru.data.api.response.ExerciseListItem?>?> =
        _searchExerciseRoutine

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    private var _error = MutableLiveData<Boolean>()
    var error: LiveData<Boolean> = _error

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getAllExerciseRoutine(token: String) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getAllExerciseRoutine(formatToken)

        client.enqueue(object : Callback<com.capstone.moru.data.api.response.ExerciseListResponse> {
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.ExerciseListResponse>,
                response: Response<com.capstone.moru.data.api.response.ExerciseListResponse>
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

            override fun onFailure(
                call: Call<com.capstone.moru.data.api.response.ExerciseListResponse>,
                t: Throwable
            ) {
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

        client.enqueue(object : Callback<com.capstone.moru.data.api.response.BookListResponse> {
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.BookListResponse>,
                response: Response<com.capstone.moru.data.api.response.BookListResponse>
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

            override fun onFailure(
                call: Call<com.capstone.moru.data.api.response.BookListResponse>,
                t: Throwable
            ) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"

                Log.e("RoutineViewModel", _message.value.toString())
            }
        })
    }

    fun findBookRoutine(token: String, key: String) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.findBookRoutine(formatToken, key)

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
                    Log.e("FIND", _bookRoutine.value.toString())
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
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

    fun findExerciseRoutine(token: String, key: String) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.findExerciseRoutine(formatToken, key)

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
                    Log.e("FIND", _exerciseRoutine.value.toString())
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