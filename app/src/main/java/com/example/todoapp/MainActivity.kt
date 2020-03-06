package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.interfaces.AlertDialogInterface
import com.example.todoapp.model.Coordinate
import com.example.todoapp.model.Event
import com.example.todoapp.model.Point
import com.example.todoapp.model.Status
import com.example.todoapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    AlertDialogInterface {


    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        button.isEnabled = false

        edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                button.isEnabled = p0.toString().trim { it <= ' ' }.isNotEmpty()

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                button.isEnabled = p0.toString().trim { it <= ' ' }.isNotEmpty()
            }
        })

        button.setOnClickListener {
            val count = edit.text.toString().toInt()
            viewModel.setCount(count)

        }

        viewModel.count.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> intentToGraphActivity(it.data?.response?.points as ArrayList<Point>)
                Status.ERROR_PARAMS -> alertDialogParams(it.errorParams?.response?.message.toString())
                Status.ERROR_OTHER -> alertDialogOther(decodeErrorMessage(it.errorOther?.response?.message.toString()))
                Status.DEFAULT -> it.default
            }
        })

    }

    private fun intentToGraphActivity(points: ArrayList<Point>) {
        val intent = Intent(this, GraphActivity::class.java)
        intent.putParcelableArrayListExtra("points", points)
        Log.d("Intent", intent.toString())
        startActivity(intent)
    }



    private fun decodeErrorMessage(errorMessage: String?): String {
        val decodeMessage = Base64.decode(errorMessage, Base64.DEFAULT)
        return String(decodeMessage, Charsets.UTF_8)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }




    override fun alertDialogParams(errorMessage: String) {
        val mAlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.application_error)
            .setMessage("$errorMessage\n\n${getString(R.string.error_count_points)}")
        mAlertDialog.show()
    }

    override fun alertDialogOther(errorMessage: String) {
        val mAlertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.application_error)
            .setMessage(errorMessage)
        mAlertDialog.show()
    }

}

