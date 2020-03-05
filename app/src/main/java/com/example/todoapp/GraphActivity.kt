package com.example.todoapp

import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.todoapp.model.Point
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class GraphActivity : AppCompatActivity() {


    val table by lazy {
        TableLayout(this)
    }

    val pointsGraph = arrayListOf<Point>()
    var series = LineGraphSeries<DataPoint>()
    val sortedMap = sortedMapOf<Double, Double>()

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
            setPadding(50, 50, 0, 0)
        }

        linear.addView(table)

        val bundle = intent.extras

        bundle.let {
            bundle?.apply {
                val points: ArrayList<Point>? = getParcelableArrayList<Point>("points")
                Log.d("Intent", points.toString())
                if (points != null) {

                    for (i in 0 until points.size) {
                        pointsGraph.add(i, points[i])
                    }
                }
            }
        }
        CoroutineScope(IO).launch {
            for (i in 0 until pointsGraph.size) {
                val x = pointsGraph[i].x
                val y = pointsGraph[i].y
                sortedMap.put(x, y)
            }
            withContext(Main) {
                createTable(pointsGraph)
                Log.d("Sorted", sortedMap.keys.toString())

                for (i in 0 until sortedMap.size) {
                    series.appendData(
                        DataPoint(sortedMap.keys.elementAt(i), sortedMap.values.elementAt(i)),
                        true,
                        1000
                    )
                    val paint = Paint()
                    paint.style = Paint.Style.FILL_AND_STROKE
                    paint.strokeWidth = 7f
                    paint.color = Color.RED
                    paint.pathEffect = CornerPathEffect(100f)
                    series.setCustomPaint(paint)
                    series.isDrawDataPoints = true;
                    series.dataPointsRadius = 10f;
                    series.thickness = 8;
                }

                graph.addSeries(series)
                graph.viewport.isScalable = true
//            val bmp = graph.takeSnapshot()
            }
        }
    }



    private suspend fun createTable(points: ArrayList<Point>) {

        var rows = 0
        var cols = 0
        var  pointsOstSize = points.size %10
        var  pointsOst : ArrayList<Point> = arrayListOf()
        for(i in 0 until pointsOstSize){
            pointsOst.add(points.get(points.size - (pointsOstSize-i)))
        }
        if (points.size % 10 == 0) {
            rows = points.size / 10
            cols = 10
            createTableDinamic(rows, cols, points)

        } else {
            rows = points.size / 10
            cols = 10
            createTableDinamic(rows, cols, points)
            rows = 1
            cols = pointsOstSize
            createTableDinamic(rows, cols, pointsOst)
        }
    }

    fun createTableDinamic(rows : Int , cols : Int, text : ArrayList<Point>) {
        for (i in 0 until rows) {

            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            for (j in 0 until cols) {

                val textview = TextView(this)
                textview.setPadding(8, 0, 8, 0)
                textview.textSize = 10F
                textview.minWidth = 50
                textview.apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                }
                if(i == 0){
                    textview.text = "${text[j].x}\n${text[j].y}"
                }
                else textview.text = "${text[j+i*10].x}\n${text[j+i*10].y}"
                row.addView(textview)
            }
            table.addView(row)
        }
    }
}




