package com.capstone.moru.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.moru.data.repository.UserRepository
import com.capstone.moru.di.Injection
import com.capstone.moru.ui.add_routine.pick_schedule.PickScheduleViewModel
import com.capstone.moru.ui.alarm.AlarmViewModel
import com.capstone.moru.ui.auth.login.LoginViewModel
import com.capstone.moru.ui.auth.register.RegisterViewModel
import com.capstone.moru.ui.detail.book_routine.DetailBookViewModel
import com.capstone.moru.ui.detail.exercise_routine.DetailExerciseViewModel
import com.capstone.moru.ui.detail.user_routine.DetailRoutineViewModel
import com.capstone.moru.ui.fill.FillProfileViewModel
import com.capstone.moru.ui.home.HomeViewModel
import com.capstone.moru.ui.profile.ProfileViewModel
import com.capstone.moru.ui.routines.RoutineViewModel
import com.capstone.moru.ui.schedule.ScheduleViewModel
import com.capstone.moru.ui.splash.SplashViewModel

@Suppress("UNCHECKED_CAST")
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
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(RoutineViewModel::class.java) -> RoutineViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(DetailBookViewModel::class.java) -> DetailBookViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(DetailExerciseViewModel::class.java) -> DetailExerciseViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> ScheduleViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(PickScheduleViewModel::class.java) -> PickScheduleViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(DetailRoutineViewModel::class.java) -> DetailRoutineViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(AlarmViewModel::class.java) -> AlarmViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                userRepository
            ) as T
            modelClass.isAssignableFrom(FillProfileViewModel::class.java) -> FillProfileViewModel(
                userRepository
            ) as T
            else -> throw java.lang.IllegalArgumentException("Unkown ViewModel class: ${modelClass.name}")
        }
    }
}