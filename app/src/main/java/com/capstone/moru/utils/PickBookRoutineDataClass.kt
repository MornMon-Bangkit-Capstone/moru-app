package com.capstone.moru.utils

import com.capstone.moru.data.api.response.BookListItem

data class PickBookRoutineDataClass(
    var routine: com.capstone.moru.data.api.response.BookListItem?,
    var isChecked: Boolean = false

)