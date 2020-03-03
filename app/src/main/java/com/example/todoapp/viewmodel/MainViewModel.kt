package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.ResponseCoordinate
import com.example.todoapp.model.User
import com.example.todoapp.repository.Repository

class MainViewModel : ViewModel(){


    private val _count: MutableLiveData<Int> = MutableLiveData()

//    val user: LiveData<User> = Transformations
//        .switchMap(_count){count ->
//            Repository.getUser(count)
//
//        }

    val count: LiveData<ResponseCoordinate> = Transformations
        .switchMap(_count){
            count -> Repository.getCount(count)
        }



//    fun setUserId(userId: String){
//        Log.d("MainViewModel", "DEBUG: " + _userId)
//        val update = userId
//        if(_userId.value == update){
//            return
//        }
//        _userId.value = update
//    }

    fun setCount(count: Int){
        Log.d("MainViewModel", "DEBUG: " + count)
        val update = count
        if(_count.value == update){
            return
        }
        _count.value=update
    }

    fun cancelJobs(){
        Repository.cancelJobs()
    }
}