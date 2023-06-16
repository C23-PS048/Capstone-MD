package com.bangkit.capstone_project.data.network.user

import com.bangkit.capstone_project.data.network.userplant.UserPlantResponse
import okhttp3.MultipartBody
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
    @POST("register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ResponseMessage>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginResponse>


    @GET("user/{id}")
    suspend fun getUserInfo(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<DetailUserResponse>

    @Multipart
    @PATCH("/user/{id}")
    suspend fun updateUserPhoto(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<ResponseMessage>

    @GET("user/{id}/plant")
    suspend fun getUserPlant(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<UserPlantResponse>

}