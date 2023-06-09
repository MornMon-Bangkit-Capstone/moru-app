package com.capstone.moru.ui.add_routine.pick_routine.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.repository.UserRepository
import com.capstone.moru.utils.PickRoutineDataClass

class PickRoutineViewModel(private var userRepository: UserRepository) : ViewModel() {
    private var _pickedRoutine = MutableLiveData<PickRoutineDataClass>()
    val pickedRoutine: LiveData<PickRoutineDataClass> = _pickedRoutine

    private val _selectedItemPosition = MutableLiveData<Int>()
    val selectedItemPosition: LiveData<Int> = _selectedItemPosition

    fun setPickedRoutine(routine: PickRoutineDataClass) {
        _pickedRoutine.value = routine
    }

    fun setSelectedItemPosition(position: Int) {
        _selectedItemPosition.value = position
    }
}