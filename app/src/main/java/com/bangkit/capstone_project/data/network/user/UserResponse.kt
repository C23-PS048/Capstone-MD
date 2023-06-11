package com.bangkit.capstone_project.data.network.user

import com.google.gson.annotations.SerializedName

data class ResponseMessage(


	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
/*data class LoginResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,
	@field:SerializedName("loginResult")
	val loginResult: LoginUser
)

data class LoginUser(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("token")
	val token: String
)*/

data class LoginResponse(



	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,
	@field:SerializedName("loginResult")
	val loginResult: LoginResult,
)

data class LoginResult(
	@field:SerializedName("id")
	val id: String,
	@field:SerializedName("name")
	val name: String,



	@field:SerializedName("token")
	val token: String
)

data class DetailUserResponse(

	@field:SerializedName("userResult")
	val userResult: UserResult? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
data class UserResult(

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val name: String? = null
)