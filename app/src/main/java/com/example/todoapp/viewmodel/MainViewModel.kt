package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.User
import com.example.todoapp.repository.Repository

class MainViewModel : ViewModel(){


//    private val _userId: MutableLiveData<String> = MutableLiveData()
//
//    val user: LiveData<User> = Transformations
//        .switchMap(_userId){usrId ->
//            Repository.getUser(usrId)
//
//        }
//
//    fun setUserId(userId: String){
//        Log.d("MainViewModel", "DEBUG: " + _userId)
//        val update = userId
//        if(_userId.value == update){
//            return
//        }
//        _userId.value = update
//    }
//
//    fun cancelJobs(){
//        Repository.cancelJobs()
//    }
}