package com.android.imageloadingapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.imageloadingapplication.models.ImagesItem
import com.android.imageloadingapplication.retrofit.APIService
import javax.inject.Inject
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
class ImageReepository @Inject constructor(private val apiService: APIService) {

    private val _images = MutableLiveData<Result<List<ImagesItem>>>()
    val images: LiveData<Result<List<ImagesItem>>>
        get() = _images


    suspend fun getImages(limit: Int) {
        val result = apiService.getImages(limit)
        if (result.isSuccessful && result.body() != null) {
            _images.postValue(Result.Success(result.body()!!))
        }else{
            _images.postValue(Result.Error(Exception("Failed to fetch images: ${result.errorBody()!!.string()}")))
        }
        }
    }
