package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class FillProfileResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("goal")
	val goal: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
