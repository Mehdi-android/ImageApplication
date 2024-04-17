package com.android.imageloadingapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.imageloadingapplication.repository.ImageReepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val imageReepository: ImageReepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(imageReepository) as T
    }

}