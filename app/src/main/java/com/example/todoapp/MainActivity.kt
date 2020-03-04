package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.interfaces.allertDialogInterface
import com.example.todoapp.model.Status
import com.example.todoapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(),
    allertDialogInterface {


    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.edit)

        val button = findViewById<Button>(R.id.button)

        val count = editText.text

        button.setOnClickListener {
//            viewModel.setCount(count.toString().toInt())
            val intent = Intent(this, GraphActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.count.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> println(it.data?.response?.points?.toList())
                Status.ERROR -> allertDialogParams(it.error?.response?.message.toString())
                Status.ERROR64 -> allertDialog(decodeErrorMessage(it.error64?.response?.message))
            }
        })


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
