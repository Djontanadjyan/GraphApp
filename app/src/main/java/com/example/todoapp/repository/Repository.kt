package com.example.todoapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todoapp.model.Event
import com.example.todoapp.api.RetrofitBuilder
import com.example.todoapp.model.Coordinate
import com.example.todoapp.model.Point
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.HttpException

object Repository {

    val coor = arrayListOf<Point>()

    var job : CompletableJob? = null

    fun getCount(count : Int) : LiveData<Event<Coordinate>> {
        job = Job()
        return object : LiveData<Event<Coordinate>>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val response = RetrofitBuilder.apiService.createCoordinate(count)
                        withContext(Main) {
                            try {
                                if (response.isSuccessful) {
                                    when (response.body()?.response?.result) {
                                        0 -> value = Event.success(response.body())
                                        -100 -> value = Event.error(response.body())
                                        -101 -> value = Event.error64(response.body())
                                    }
                                }
                            } catch (e: HttpException) {
                                Log.d("Repository", "Exception ${e.message}")
                            } catch (e: Throwable) {
                                Log.d("Repository", "Ooops: Something else went wrong")
                            }
                            theJob.complete()
                        }
                    }
                }
            }
        }

//    val coordinates = RetrofitBuilder.apiService.createCoordinate(count)
//        .enqueue(object : Callback<Coordinate> {
//            override fun onFailure(call: Call<Coordinate>, t: Throwable) {
//
//            }
//
//            override fun onResponse(
//                call: Call<Coordinate>,
//                response: Response<Coordinate>
//            ) {
//                if (response.isSuccessful) {
//                    for(i in 0..count-1){
//                        response.body()?.response?.points?.get(i)?.let { coor.add(it) }
//                    }
//                    Log.d("Repository" , response.body()?.response?.points?.size.toString())
//                }
//            }
//        })

//    fun getUser(userId: String) : LiveData<User>{
//        job = Job()
//        return object : LiveData<User>(){
//            override fun onActive() {
//                super.onActive()
//                job?.let {theJob ->
//                    CoroutineScope(IO + theJob).launch {
//                        val user = RetrofitBuilder.apiService.getUser(userId)
//                        withContext(Main){
//                            value = user
//                            theJob.complete()
//                        }
//                    }
//
//                }

//            }
//        }
//    }
    }

        fun cancelJobs() {
            job?.cancel()
        }


    }
