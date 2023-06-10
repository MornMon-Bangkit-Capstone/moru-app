package com.capstone.moru.ui.detail.book_routine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBookViewModel(private var userRepository: UserRepository) : ViewModel() {
    private var _bookRoutine =
        MutableLiveData<com.capstone.moru.data.api.response.BookListItem?>()
    val bookRoutine: LiveData<com.capstone.moru.data.api.response.BookListItem?> =
        _bookRoutine

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getBookRoutineDetail(token: String, id: Int, isPublic: Int) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getBookRoutineDetail(formatToken, id, isPublic)

        client.enqueue(object : Callback<com.capstone.moru.data.api.response.BookListResponse> {
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.BookListResponse>,
                response: Response<com.capstone.moru.data.api.response.BookListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()

                    Log.e("DETAIL BOOK 1", response.toString())
                    _bookRoutine.value = response.body()?.list?.firstOrNull()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                    Log.e("DETAIL BOOK 2", response.toString())
                }
            }

            override fun onFailure(
                call: Call<com.capstone.moru.data.api.response.BookListResponse>,
                t: Throwable
            ) {
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
                Log.e("DETAIL BOOK 3", t.message.toString())

            }
        })
    }


}