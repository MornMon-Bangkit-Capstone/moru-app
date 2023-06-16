package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

    @field:SerializedName("data")
    val data: ProfileData? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class ProfileData(

    @field:SerializedName("profilePicture")
    val profilePicture: String? = null,

    @field:SerializedName("goal")
    val goal: String? = null,

    @field:SerializedName("favBook")
    val favBook: String? = null,

    @field:SerializedName("quota")
    val quota: Int? = null,

    @field:SerializedName("favExercise")
    val favExercise: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("birthDate")
    val birthDate: String? = null,

    @field:SerializedName("favAuthor")
    val favAuthor: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("fillData")
    val fillData: Int? = null,

    @field:SerializedName("username")
    val username: Any? = null
)
