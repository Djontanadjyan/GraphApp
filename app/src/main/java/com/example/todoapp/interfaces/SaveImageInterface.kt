package com.example.todoapp.interfaces

import android.graphics.Bitmap

interface SaveImageInterface {
    fun saveImageToStorage(bitmap: Bitmap)
}