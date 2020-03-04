package com.example.todoapp

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.model.Point
import com.example.todoapp.model.Status
import com.example.todoapp.viewmodel.MainViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*
import java.util.ArrayList


class GraphActivity :AppCompatActivity() {

    var ROWS = 0
    val COL = 2
    val table by lazy {
        TableLayout(this)
    }

    val pointsGraph = arrayListOf<Point>()
    var pointArray  = arrayListOf<DataPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val lp = TableLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        table.apply {
            layoutParams = lp
            isShrinkAllColumns = true
            setPadding(50,50,0,0)
        }



        val bundle = intent.extras

        bundle.let {
            bundle?.apply {
                val points: ArrayList<Point>? = getParcelableArrayList<Point>("points")
                Log.d("Intent", points.toString())
                if (points != null) {
                    ROWS = points.size
                    for (i in 0 until points.size) {
                        pointsGraph.add(i,points[i])
                    }
                }
            }
        }

        createTable(ROWS, COL)


        val gr = findViewById<GraphView>(R.id.graph)




        println(getCoordinates(pointsGraph).toList())




        val series = LineGraphSeries<DataPoint>(

        )

            graph.addSeries(series)



    }




    fun getCoordinates(pointsGraph: ArrayList<Point>): ArrayList<DataPoint>{
        for (i in 0 until pointsGraph.size){
            pointArray.add(DataPoint(pointsGraph[i].x, pointsGraph[i].y))
        }
        return pointArray
    }



    fun createTable(rows: Int, cols: Int) {

        for (i in 0 until rows) {

            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )


            for (j in 0 until cols) {

                val textview = TextView(this)
                textview.setPadding(8,0,8,0)
                textview.apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT
                    )
                    text = "R $i C $j"
                }
                row.addView(textview)
            }
            table.addView(row)
        }
        constraintLayout.addView(table)
    }
}


