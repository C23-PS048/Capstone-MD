package com.bangkit.capstone_project.data.network.disease

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DiseaseService {




    @GET("disease")
    suspend fun getAlLDisease(


    ): Response<AllDiseaseResponse>

    @GET("disease/{slug}")
    suspend fun getDisease(
        @Path("slug") slug: String,

    ): Response<DiseaseResponse>



}