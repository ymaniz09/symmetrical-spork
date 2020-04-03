package com.github.ymaniz09.symmetricalspork.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.ymaniz09.symmetricalspork.R
import com.github.ymaniz09.symmetricalspork.ui.DataStateListener
import com.github.ymaniz09.symmetricalspork.util.DataState
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), DataStateListener {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDateStateChange(dataState)
    }

    private fun handleDateStateChange(dataState: DataState<*>?) {
        dataState?.let {

            showProgressBar(it.loading)

            it.message?.let { event ->

                event.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
