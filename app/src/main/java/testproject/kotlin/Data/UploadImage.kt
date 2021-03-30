package testproject.kotlin.Data

import com.google.gson.annotations.SerializedName

data class UploadImage(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
