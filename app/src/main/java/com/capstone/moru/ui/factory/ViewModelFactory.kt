package com.capstone.moru.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.moru.data.repository.UserRepository
import com.capstone.moru.di.Injection
import com.capstone.moru.ui.auth.login.LoginViewModel
import com.capstone.moru.ui.auth.register.RegisterViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ViewModelFactory(Injection.provideUserRepository(context))
        }.also { INSTANCE = it }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                userRepository
            ) as T
            else -> throw java.lang.IllegalArgumentException("Unkown ViewModel class: ${modelClass.name}")

        }
    }
}