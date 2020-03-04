package com.example.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.Event
import com.example.todoapp.model.Coordinate
import com.example.todoapp.repository.Repository

class MainViewModel : ViewModel(){


    private val _count: MutableLiveData<Int> = MutableLiveData()


    val count: LiveData<Event<Coordinate>> = Transformations
        .switchMap(_count){
            count -> Repository.getCount(count)
        }


    fun setCount(count: Int){

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