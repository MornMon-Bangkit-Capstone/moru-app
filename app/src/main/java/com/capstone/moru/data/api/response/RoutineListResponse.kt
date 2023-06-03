package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class RoutineResponse(

	@field:SerializedName("list")
	val list: List<ListItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ListItem(

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
