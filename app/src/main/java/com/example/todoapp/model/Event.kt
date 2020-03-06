package com.example.todoapp.model

data class Event<out T>(val status: Status, val data: T?, val errorParams: T?, val errorOther: T?, val default: T?) {


    companion object {
        fun <T> success(data: T?): Event<T> {
            return Event(
                Status.SUCCESS,
                data,
                null,
                null,
                null
            )
        }
        fun <T> errorParams(errorParams: T?): Event<T> {
            return Event(
                Status.ERROR_PARAMS,
                null,
                errorParams,
                null,
                null
            )
        }
        fun <T> errorOther(errorOther: T?): Event<T> {
            return Event(
                Status.ERROR_OTHER,
                null,
                null,
                errorOther,
                null
            )
        }
        fun <T> default(default: T?) : Event<T>{
            return  Event(
                Status.DEFAULT,
                null,
                null,
                null,
                default
            )
        }
    }
}

enum class Status {
    DEFAULT,
    SUCCESS,
    ERROR_PARAMS,
    ERROR_OTHER;
}