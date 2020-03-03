package com.example.todoapp

import com.example.todoapp.model.User

object ExampleSinglton{

    val singltonUser : User by lazy {
        User("djontanadjyan@gmail.com", "djon", "image.png")
    }

}