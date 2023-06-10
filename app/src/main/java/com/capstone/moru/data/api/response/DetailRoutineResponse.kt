package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class DetailRoutineResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("list")
	val list: com.capstone.moru.data.api.response.ListRoutine? = null
)

data class ListRoutine(

	@field:SerializedName("img-url")
	val imgUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
