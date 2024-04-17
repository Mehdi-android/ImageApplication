package com.android.imageloadingapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.android.imageloadingapplication.adapter.ImageAdapter
import com.android.imageloadingapplication.databinding.ActivityMainBinding
import com.android.imageloadingapplication.viewmodel.MainViewModel
import com.android.imageloadingapplication.viewmodel.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewwModelFactory: MainViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        (application as ImageApplication).applicationComponents.inject(this)
        mainViewModel =
            ViewModelProvider(this, mainViewwModelFactory).get(MainViewModel::class.java)
        mainViewModel.getImages(limit = 100)
        mainViewModel.imagesLiveData.observe(this, {
            when (it) {
                is com.android.imageloadingapplication.repository.Result.Success -> {
                    val adapter = ImageAdapter(this, it.data)
                    binding.gridView.adapter = adapter
                }

                is com.android.imageloadingapplication.repository.Result.Error -> {
                    // Handle error: display a message or retry option
                    Toast.makeText(this, "Error: ${it.exception.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        mainViewModel.loading.observe(this, { isLoading ->
            // Show or hide loading indicator based on the isLoading value
            if (isLoading) {
                // Show loading indicator
                binding.progressbar.visibility = View.VISIBLE
            } else {
                // Hide loading indicator
                binding.progressbar.visibility = View.GONE
            }
        })

        mainViewModel.error.observe(this, { errorMessage ->
            // Display error message if not null
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}