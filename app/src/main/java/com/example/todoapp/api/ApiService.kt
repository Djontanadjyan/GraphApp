package com.example.todoapp.api

import com.example.todoapp.model.Coordinate
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("pointsList?version=1.1")
    suspend fun createCoordinate(
        @Part("count") count : Int ) :Response<Coordinate>
}