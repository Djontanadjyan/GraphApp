package com.example.todoapp.model

data class Event<out T>(val status: Status, val data: T?, val error: T?, val error64: T?) {

    companion object {
        fun <T> success(data: T?): Event<T> {
            return Event(
                Status.SUCCESS,
                data,
                null,
                null
            )
        }
        fun <T> errorParams(errorParams: T?): Event<T> {
            return Event(
                Status.ERROR_PARAMS,
                null,
                errorParams,
                null
            )
        }
        fun <T> errorOther(errorOther: T?): Event<T> {
            return Event(
                Status.ERROR_OTHER,
                null,
                null,
                errorOther
            )
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR_PARAMS,
    ERROR_OTHER;
}