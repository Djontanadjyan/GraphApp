package com.example.todoapp.model

import com.google.gson.annotations.SerializedName

data class ResponseCoordinate(
    @SerializedName("points")
    val points: List<Point>
)