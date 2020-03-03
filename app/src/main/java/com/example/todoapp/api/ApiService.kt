package com.example.todoapp.api

import android.telecom.Call
import com.example.todoapp.model.CoordinatesResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("create_coordinate")
    suspend fun createCoordinate(
        @Field("version") version : Double,
        @Field("count") count : Int) : retrofit2.Call<CoordinatesResponse>
}