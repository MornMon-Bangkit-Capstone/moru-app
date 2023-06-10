package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("profData")
	val profData: com.capstone.moru.data.api.response.ProfData? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ProfData(

	@field:SerializedName("Favorite Author")
	val favoriteAuthor: String? = null,

	@field:SerializedName("Favorite Exercise")
	val favoriteExercise: String? = null,

	@field:SerializedName("Favorite Book")
	val favoriteBook: String? = null,

	@field:SerializedName("Favorite Book Category")
	val favoriteBookCategory: String? = null,

	@field:SerializedName("Exercise Experience")
	val exerciseExperience: String? = null,

	@field:SerializedName("profPicture")
	val profPicture: String? = null,

	@field:SerializedName("Date of Birth")
	val dateOfBirth: String? = null,

	@field:SerializedName("Goals")
	val goals: String? = null,

	@field:SerializedName("Favorite Exercise Author")
	val favoriteExerciseAuthor: String? = null,

	@field:SerializedName("Name")
	val name: String? = null
)
