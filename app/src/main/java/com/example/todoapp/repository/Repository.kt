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
                                        -100 -> value = Event.errorParams(response.body())
                                        -101 -> value = Event.errorOther(response.body())
                                    }
                                    value = Event.default(null)

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
    }

        fun cancelJobs() {
            job?.cancel()
        }
    }
