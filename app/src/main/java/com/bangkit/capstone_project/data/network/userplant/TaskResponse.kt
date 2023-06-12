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
	val disease: String? = null,

	@field:SerializedName("lastScheduledDate")
	val lastScheduledDate: String? = null,

	@field:SerializedName("plantId")
	val plantId: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("frequency")
	val frequency: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nextScheduledDate")
	val nextScheduledDate: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null
)
data class Response(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
