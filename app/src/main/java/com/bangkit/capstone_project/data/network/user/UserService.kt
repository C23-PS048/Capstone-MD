package com.bangkit.capstone_project.data.network.user

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserService {
    @FormUrlEncoded
    @POST("/v1/register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ResponseMessage>

    @FormUrlEncoded
    @POST("/v1/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginResponse>



    @GET("/user/{id}")
    fun getUserInfo(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<DetailUserResponse>

    @Multipart
    @PATCH("/user/{id}")
    fun updateUser(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Part foto: MultipartBody.Part,


    ): Call<ResponseMessage>

}