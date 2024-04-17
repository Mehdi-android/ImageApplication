package com.android.imageloadingapplication.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class CacheRepository(private val context: Context) {

    private val CACHE_DIRECTORY = "image_cache"

    // Save bitmap to disk cache
    suspend fun saveBitmapToCache(bitmap: Bitmap, filename: String) {
        withContext(Dispatchers.IO) {
            val cacheDir = File(context.cacheDir, CACHE_DIRECTORY)
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val file = File(cacheDir, filename)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
            }
        }
    }

    // Load bitmap from disk cache
    suspend fun loadBitmapFromCache(filename: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            val cacheDir = File(context.cacheDir, CACHE_DIRECTORY)
            val file = File(cacheDir, filename)
            if (file.exists()) {
                BitmapFactory.decodeFile(file.absolutePath)
            } else {
                null
            }
        }
    }

    fun generateUniqueFilename(imageUrl: String): String {
        val hash = imageUrl.hashCode()
        return "image_$hash.png"
    }
}

