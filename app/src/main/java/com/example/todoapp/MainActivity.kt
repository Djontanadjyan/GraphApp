package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.api.RetrofitBuilder
import com.example.todoapp.model.Coordinate
import com.example.todoapp.model.Point
import com.example.todoapp.repository.Repository
import com.example.todoapp.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val version : Double = 1.1
    var x: Int = 0
    var y: Int = 0
    val count = 3
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val coordinates = RetrofitBuilder.apiService.createCoordinate(count)
//            .enqueue(object : Callback<Coordinate> {
//                override fun onFailure(call: Call<Coordinate>, t: Throwable) {
//                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
//                }
//
//                override fun onResponse(
//                    call: Call<Coordinate>,
//                    response: Response<Coordinate>
//                ) {
//                    if (response.isSuccessful) {
//                       for(i in 0..count-1){
//                           response.body()?.response?.points?.get(i)?.let { coor.add(it) }
//                       }
//                        println(coor.toList())
//                    }
//                }
//            })





        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.count.observe(this, Observer {count->
            println("DEBUG : $count")
        })

        viewModel.setCount(count)
        Log.d("Main","Repo "+Repository.coor.size)
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}
