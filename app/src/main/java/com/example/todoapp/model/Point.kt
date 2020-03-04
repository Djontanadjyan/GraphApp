package com.example.todoapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Point(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(x)
        parcel.writeDouble(y)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Point> {
        override fun createFromParcel(parcel: Parcel): Point {
            return Point(parcel)
        }

        override fun newArray(size: Int): Array<Point?> {
            return arrayOfNulls(size)
        }
    }
}