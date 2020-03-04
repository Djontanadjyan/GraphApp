package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*


class GraphActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val gr  = findViewById<GraphView>(R.id.graph)
        val series = LineGraphSeries<DataPoint>(
            arrayOf(DataPoint(1.5,1.3),
                DataPoint(2.7,5.5))
        )

        graph.addSeries(series)

    }

}