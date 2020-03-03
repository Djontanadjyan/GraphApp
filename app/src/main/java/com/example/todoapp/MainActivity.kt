package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.api.RetrofitBuilder
import com.example.todoapp.model.CoordinatesResponse
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

     val version = 1.1

    var coor : Int? = 0

    var x :Int? = 0

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        CoroutineScope(IO).launch {
            val coordinates = RetrofitBuilder.apiService.createCoordinate(version, 5)
                .enqueue(object: Callback<CoordinatesResponse>{
                    override fun onFailure(call: Call<CoordinatesResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,  t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<CoordinatesResponse>,
                        response: Response<CoordinatesResponse>
                    ) {
                        coor = response.body()?.x
                    }

                })
            withContext(Main){

                x = coor
                println(x)

            }

        }

//
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//
//        viewModel.user.observe(this, Observer {user->
//            println("DEBUG : $user")
//        })
//
//        viewModel.setUserId("1")
    }



//    override fun onDestroy() {
//        super.onDestroy()
//        viewModel.cancelJobs()
//    }
}
