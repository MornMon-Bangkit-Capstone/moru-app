package com.capstone.moru.utils

import com.capstone.moru.data.api.response.ExerciseListItem

data class PickExerciseRoutineDataClass(
    var routine: com.capstone.moru.data.api.response.ExerciseListItem?,
    var isChecked: Boolean = false
)