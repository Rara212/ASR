package com.example.asr.api

import com.example.asr.data.ActivityItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ActivityAPI {
    @GET("/rest/v1/activities?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String
    ) : Response<List<ActivityItem>>
}