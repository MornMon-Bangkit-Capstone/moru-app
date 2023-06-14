package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class ExerciseListResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("list")
    val list: List<com.capstone.moru.data.api.response.ExerciseListItem?>? = null
)

data class ExerciseListItem(

    @field:SerializedName("Description")
    val description: String? = null,

    @field:SerializedName("Category")
    val category: String? = null,

    @field:SerializedName("Duration_Min")
    val durationMin: String? = null,

    @field:SerializedName("Number_of_people")
    val numberOfPeople: String? = null,

    @field:SerializedName("Equipment")
    val equipment: String? = null,

    @field:SerializedName("isPublic")
    val isPublic: String? = null,

    @field:SerializedName("Muscle")
    val muscle: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("Visual")
    val visual: String? = null,

    @field:SerializedName("Sports")
    val sports: String? = null,

    @field:SerializedName("Location")
    val location: String? = null,

    @field:SerializedName("Video")
    val video: String? = null,
)
