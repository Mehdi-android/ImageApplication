package com.android.imageloadingapplication.retrofit

import com.android.imageloadingapplication.models.ImagesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("api/v2/content/misc/media-coverages")
    suspend fun getImages(@Query("limit") limit: Int): Response<List<ImagesItem>>
}