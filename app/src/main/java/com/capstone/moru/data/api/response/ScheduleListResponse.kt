package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class ScheduleListResponse(

	@field:SerializedName("listSchedule")
	val listSchedule: List<ListScheduleItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ListScheduleItem(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("img-url")
	val imgUrl: String? = null,

	@field:SerializedName("start_hour")
	val startHour: String? = null,

	@field:SerializedName("end_hour")
	val endHour: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
