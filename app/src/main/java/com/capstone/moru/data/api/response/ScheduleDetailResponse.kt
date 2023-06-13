package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class ScheduleDetailResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("list")
	val list: List<ScheduleDetailListItem?>? = null
)

data class ScheduleDetailListItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("uid")
	val uid: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("end_time")
	val endTime: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("isPublic")
	val isPublic: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("refId")
	val refId: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("durationMin")
	val durationMin: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
