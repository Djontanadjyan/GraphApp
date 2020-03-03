package com.example.todoapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todoapp.api.RetrofitBuilder
import com.example.todoapp.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.math.log

object Repository {

    var job : CompletableJob? = null



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


//    fun cancelJobs(){
//        job?.cancel()
//    }



}