package com.example.todoapp.interfaces

import com.example.todoapp.model.Point

interface TableInterface {
    fun createTableDynamics(rows : Int, cols : Int, points : ArrayList<Point>)
}