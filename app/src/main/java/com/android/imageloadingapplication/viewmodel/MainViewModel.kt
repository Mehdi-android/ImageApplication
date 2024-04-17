package com.android.imageloadingapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.imageloadingapplication.models.ImagesItem
import com.android.imageloadingapplication.repository.ImageReepository
import com.android.imageloadingapplication.repository.Result
import kotlinx.coroutines.launch

class MainViewModel(private val imageReepository: ImageReepository):ViewModel() {
    val imagesLiveData: LiveData<Result<List<ImagesItem>>>
        get() = imageReepository.images

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error



    fun getImages(limit: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                imageReepository.getImages(limit)
                _loading.value = false
            } catch (e: Exception) {
                _loading.value = false
                _error.value = e.message ?: "Unknown error occurred"
            }
        }
    }
}