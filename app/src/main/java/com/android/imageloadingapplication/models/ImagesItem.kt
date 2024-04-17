package com.android.imageloadingapplication.models

data class ImagesItem(
    val backupDetails: BackupDetails,
    val coverageURL: String,
    val id: String,
    val language: String,
    val mediaType: Int,
    val publishedAt: String,
    val publishedBy: String,
    val thumbnail: Thumbnail,
    val title: String
){
    fun getImageUrl(): String {
        return "${thumbnail.domain}/${thumbnail.basePath}/0/${thumbnail.key}"
    }
}