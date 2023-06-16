package com.bangkit.capstone_project.data.network.userplant

import com.google.gson.annotations.SerializedName

data class UserPlantResponse(

	@field:SerializedName("userPlant")
	val userPlant: List<UserPlantItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UserPlantItem(

	@field:SerializedName("lastScheduledDate")
	val lastScheduledDate: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("plantId")
	val plantId: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nextScheduledDate")
	val nextScheduledDate: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null
)
