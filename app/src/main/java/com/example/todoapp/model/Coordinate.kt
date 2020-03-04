package com.example.todoapp.model

import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("reqId")
    val reqId: Any,
    @SerializedName("response")
    val response: ResponseCoordinate
)