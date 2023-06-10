package com.capstone.moru.data.api.response

import com.google.gson.annotations.SerializedName

data class BookListResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("list")
	val list: List<com.capstone.moru.data.api.response.BookListItem?>? = null
)

data class BookListItem(

	@field:SerializedName("ImageURLL")
	val imageURLL: String? = null,

	@field:SerializedName("ISBN")
	val iSBN: Int? = null,

	@field:SerializedName("CountRating")
	val countRating: Any? = null,

	@field:SerializedName("YearOfPublication")
	val yearOfPublication: Int? = null,

	@field:SerializedName("AvgRating")
	val avgRating: Any? = null,

	@field:SerializedName("Author")
	val author: String? = null,

	@field:SerializedName("Summary")
	val summary: String? = null,

	@field:SerializedName("isPublic")
	val isPublic: String? = null,

	@field:SerializedName("Publisher")
	val publisher: String? = null,

	@field:SerializedName("Genres")
	val genres: String? = null,

	@field:SerializedName("BookTitle")
	val bookTitle: String? = null,

	@field:SerializedName("BookAuthor")
	val bookAuthor: String? = null
)
