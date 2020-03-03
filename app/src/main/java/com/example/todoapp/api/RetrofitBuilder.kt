package com.example.todoapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    const val BASE_URL: String = "https://demo.bankplus.ru/mobws/json/pointsList/"

    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    private val okHttpClient: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)

    }

    val apiService : ApiService by lazy {
        retrofitBuilder
            .client(okHttpClient.build())
            .build()
            .create(ApiService::class.java)
    }


}


