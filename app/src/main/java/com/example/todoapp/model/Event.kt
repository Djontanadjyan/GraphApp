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

        fun <T> error(error: T?): Event<T> {
            return Event(
                Status.ERROR,
                null,
                error,
                null
            )
        }
        fun <T> error64(error64: T?): Event<T> {
            return Event(
                Status.ERROR,
                null,
                null,
                error64
            )
        }

    }
}

enum class Status {
    SUCCESS,
    ERROR,
    ERROR64
}