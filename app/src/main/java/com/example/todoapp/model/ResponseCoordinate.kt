package com.example.todoapp.model

import com.google.gson.annotations.SerializedName

data class ResponseCoordinate(
    @SerializedName("result")
    val result: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("points")
    val points: ArrayList<Point>
)