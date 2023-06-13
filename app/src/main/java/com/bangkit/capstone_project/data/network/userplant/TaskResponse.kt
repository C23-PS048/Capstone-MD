package com.bangkit.capstone_project.data.network.userplant

import com.google.gson.annotations.SerializedName

data class TaskResponse(

	@field:SerializedName("userPlant")
	val userPlant: UserPlant? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UserPlant(

	@field:SerializedName("disease")
	val disease: String,

	@field:SerializedName("lastScheduledDate")
	val lastScheduledDate: String,

	@field:SerializedName("plantId")
	val plantId: Int,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("frequency")
	val frequency: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("nextScheduledDate")
	val nextScheduledDate: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("startDate")
	val startDate: String
)
data class Response(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
