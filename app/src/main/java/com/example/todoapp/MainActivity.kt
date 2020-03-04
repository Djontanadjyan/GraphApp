package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.interfaces.allertDialogInterface
import com.example.todoapp.model.Point
import com.example.todoapp.model.Status
import com.example.todoapp.viewmodel.MainViewModel
import java.io.Serializable

class MainActivity : AppCompatActivity(),
    allertDialogInterface {


    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.edit)

        val button = findViewById<Button>(R.id.button)

        button.isEnabled = false

        val count = editText.text

        editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                button.isEnabled = p0.toString().trim{ it <= ' ' }.isNotEmpty()
            }
        })


        button.setOnClickListener {
            viewModel.setCount(count.toString().toInt())
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.count.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> intentToGraphActivity(it.data?.response?.points as ArrayList<Point>)
                Status.ERROR -> allertDialogParams(it.error?.response?.message.toString())
                Status.ERROR64 -> allertDialog(decodeErrorMessage(it.error64?.response?.message))
            }
        })


    }

    fun intentToGraphActivity(points : ArrayList<Point>) {
        val intent = Intent(this, GraphActivity::class.java)
        intent.putParcelableArrayListExtra("points", points)
        Log.d("Intent" , intent.toString())
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

    override fun allertDialogParams(errorMessage: String) {
        val mAllertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.application_error)
            .setMessage(errorMessage)
            .setMessage(R.string.error_count_points)
        mAllertDialog.show()
    }

    override fun allertDialog(errorMessage: String) {
        val mAllertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.application_error)
            .setMessage(errorMessage)
        mAllertDialog.show()
    }
}
