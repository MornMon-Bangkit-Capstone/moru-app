package com.capstone.moru.utils

import com.capstone.moru.data.api.response.ExerciseListItem

data class PickExerciseRoutineDataClass(
    var routine: ExerciseListItem?,
    var isChecked: Boolean = false
)