package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class AddScheduleResponse(

	@field:SerializedName("scheduleResult")
	val scheduleResult: ScheduleResult? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ScheduleResult(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("start_hour")
	val startHour: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("end_hour")
	val endHour: String? = null
)
