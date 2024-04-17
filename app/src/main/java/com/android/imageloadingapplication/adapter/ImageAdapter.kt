package com.android.imageloadingapplication.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.imageloadingapplication.R
import com.android.imageloadingapplication.models.ImagesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ImageAdapter(private val context: Context, private val images: List<ImagesItem>) :
    BaseAdapter() {
    private val imageCache = HashMap<String, Bitmap?>()
    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView
        val progressBar: ProgressBar
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.grid_item_image, parent, false)
        imageView = itemView.findViewById(R.id.imageView)
        progressBar = itemView.findViewById(R.id.progressBar)

        // Set dimensions for GridView item
        /*  val params = GridView.LayoutParams(
              parent!!.width / 2,
              parent.height / 2
          )
          itemView.layoutParams = params*/
        imageView.setImageDrawable(null)
        // Load image into ImageView using coroutine
        val imageUrl = (getItem(position) as ImagesItem).getImageUrl()
        imageView.loadImageAsync(imageUrl, progressBar)

        return itemView
    }

    private fun ImageView.loadImageAsync(imageUrl: String, progressBar: ProgressBar) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE // Show progress bar
            val cachedBitmap = imageCache[imageUrl]
            if (cachedBitmap != null) {
                // Use cached bitmap if available
                setImageBitmap(cachedBitmap)
                progressBar.visibility = View.GONE // Hide progress bar
            } else {
                // Fetch image if not cached
                val bitmap = fetchImage(imageUrl)
                bitmap?.let {
                    setImageBitmap(bitmap)
                    // Cache the downloaded bitmap
                    imageCache[imageUrl] = bitmap
                }
                progressBar.visibility = View.GONE // Hide progress bar when image loaded
            }
        }

        // Cancel the coroutine job when the ImageView is recycled
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {}
            override fun onViewDetachedFromWindow(view: View) {
                job.cancel()
            }
        })
    }

    private suspend fun fetchImage(imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            var bitmap: Bitmap? = null
            try {
                val url = URL(imageUrl)
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val inputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
            bitmap
        }
    }

}
