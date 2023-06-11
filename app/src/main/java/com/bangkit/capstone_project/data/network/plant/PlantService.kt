package com.bangkit.capstone_project.data.network.plant

import com.bangkit.capstone_project.data.network.user.DetailUserResponse
import com.bangkit.capstone_project.data.network.user.LoginResponse
import com.bangkit.capstone_project.data.network.user.ResponseMessage
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

interface PlantService {




    @GET("plant")
    suspend fun getAllPlant(

        @Header("Authorization") token: String
    ): Response<PlantResponse>

    @GET("plant/{slug}")
    suspend fun getPlant(
        @Path("slug") slug: String,
        @Header("Authorization") token: String
    ): Response<SinglePlantResponse>

}