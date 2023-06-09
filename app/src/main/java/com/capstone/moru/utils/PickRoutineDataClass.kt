package com.capstone.moru.utils

import com.capstone.moru.data.api.response.ListItem

data class PickRoutineDataClass(
    var routineTitle: ListItem?,
    var isChecked: Boolean = false
)
