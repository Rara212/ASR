package com.example.asr.api

import com.example.asr.data.ActivityData
import com.example.asr.data.ActivityItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.DELETE
import retrofit2.http.Query

interface ActivityAPI {
    @GET("/rest/v1/activities?select=*")
    suspend fun get(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query ("category") category : String,
        @Query ("userid") userid : String
    ) : Response<List<ActivityItem>>

    @POST("/rest/v1/activities")
    suspend fun create(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Body activityData: ActivityData
    )

    @PATCH("/rest/v1/activities")
    suspend fun update(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("activityid") idQuery : String,
        @Body activityData: ActivityData
    ) : Response<Unit>

    @DELETE("/rest/v1/activities")
    suspend fun delete(
        @Header("Authorization") token: String,
        @Header("apikey") apiKey: String,
        @Query("activityid") idQuery : String
    ) : Response<Unit>

}