package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class ScheduleListResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("list")
    val list: List<ScheduleListItem?>? = null
)

data class ScheduleListItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("start_time")
    val startTime: String? = null,

    @field:SerializedName("uid")
    val uid: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("end_time")
    val endTime: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("type")
    val type: String? = null
)