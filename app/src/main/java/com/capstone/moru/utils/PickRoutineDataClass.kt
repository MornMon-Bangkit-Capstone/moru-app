package com.capstone.moru.utils

import com.capstone.moru.data.api.response.BookListItem
import com.capstone.moru.data.api.response.ListItem

data class PickRoutineDataClass(
    var routine: BookListItem?,
    var isChecked: Boolean = false
)
