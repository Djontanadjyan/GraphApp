package com.example.todoapp

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.todoapp.interfaces.SaveImageInterface
import com.example.todoapp.interfaces.TableInterface
import com.example.todoapp.model.Point
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


class GraphActivity : AppCompatActivity(), TableInterface, SaveImageInterface {

    val table by lazy {
        TableLayout(this)
    }

    private val pointsGraph = arrayListOf<Point>()
    private var series = LineGraphSeries<DataPoint>()
    private val sortedMap = sortedMapOf<Double, Double>()


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
            setPadding(16, 16, 0, 16)
        }


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
                sortedMap[x] = y
            }
            createTable(pointsGraph)
            withContext(Main) {
                linear.addView(table)
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
                    series.isDrawDataPoints = true
                    series.dataPointsRadius = 10f
                    series.thickness = 8
                }
                graph.addSeries(series)
                graph.setBackgroundColor(Color.WHITE)
                graph.viewport.isScalable = true
                graph.setPadding(0, 16, 16, 0)
                val bmpGraph = graph.takeSnapshot()

                save_button.setOnClickListener {
                    onClickButton(bmpGraph)
                }
            }
        }
    }

    private fun onClickButton(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) { ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
            } else {
                saveImageToStorage(bitmap)
            }

        } else {
            saveImageToStorage(bitmap)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted, please tap on button again", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun saveImageToStorage(bitmap: Bitmap) {

        val storageDirectory = getExternalFilesDir(null)?.absolutePath
        val dir = File(storageDirectory, "/Graph/")
        dir.mkdir()
        val file = File(dir, "graph_image.jpg")
        println(file)
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            Log.d("Save", bitmap.toString())
            stream.flush()
            stream.close()
            Toast.makeText(this, getString(R.string.message_saved_graph), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createTable(points: ArrayList<Point>) {
        var rows: Int
        var cols: Int
        val pointsOstSize = points.size % 10
        val pointsOst: ArrayList<Point> = arrayListOf()
        for (i in 0 until pointsOstSize) {
            pointsOst.add(points.get(points.size - (pointsOstSize - i)))
        }
        if (points.size % 10 == 0) {
            rows = points.size / 10
            cols = 10
            createTableDynamics(rows, cols, points)

        } else {
            rows = points.size / 10
            cols = 10
            createTableDynamics(rows, cols, points)
            rows = 1
            cols = pointsOstSize
            createTableDynamics(rows, cols, pointsOst)
        }
    }

    override fun createTableDynamics(rows: Int, cols: Int, points: ArrayList<Point>) {
        for (i in 0 until rows) {
            val row = TableRow(this)
            row.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            for (j in 0 until cols) {
                val textview = TextView(this)
                textview.setPadding(8, 0, 8, 0)
                textview.textSize = 8F
                textview.minWidth = 50
                textview.setTextColor(Color.BLACK)
                textview.setBackgroundResource(R.drawable.border)
                textview.apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                }
                if (i == 0) {
                    textview.text = String.format("${points[j].x}\n${points[j].y}")
                } else textview.text = String.format("${points[j + i * 10].x}\n${points[j + i * 10].y}")
                row.addView(textview)
            }
            table.addView(row)
        }
    }
}




