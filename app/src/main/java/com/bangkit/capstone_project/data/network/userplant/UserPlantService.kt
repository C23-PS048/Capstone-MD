package com.bangkit.capstone_project.data.network.userplant

import com.bangkit.capstone_project.data.network.user.ResponseMessage
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserPlantService {
    @FormUrlEncoded

    @POST("/userPlant")
    suspend fun saveUserPlant(
        @Field("location") location: String,
        @Field("disease") disease: String,
        @Field("startDate") startDate: String,
        @Field("lastScheduledDate") lastScheduledDate: String,
        @Field("nextScheduledDate") nextScheduledDate: String,
        @Field("frequency") frequency: String,
        @Field("plantId") plantId: String,
        @Field("userId") userId: String,
        @Header("Authorization") token: String,
    ): Response<ResponseMessage>


    @GET("userPlant/{id}")
    suspend fun getUserPlantInfo(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<TaskResponse>


    @FormUrlEncoded
    @PATCH("/userPlant/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Field("location") location: String,
        @Field("disease") disease: String,
        @Field("startDate") startDate: String,
        @Field("lastScheduledDate") lastScheduledDate: String,
        @Field("nextScheduledDate") nextScheduledDate: String,
        @Field("frequency") frequency: String,
        @Field("plantId") plantId: String,
        @Field("userId") userId: String,
        @Header("Authorization") token: String


    ): Response<ResponseMessage>

    @DELETE("/userPlant/{id}")
    suspend fun deleteTask(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<ResponseMessage>

}