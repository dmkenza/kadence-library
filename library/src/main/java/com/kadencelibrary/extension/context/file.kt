package com.kadencelibrary.extension.context

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.glidebitmappool.GlideBitmapFactory
import java.io.File
import java.io.FileOutputStream


//val directory = fullPath.substringBeforeLast("/")
//val fullName = fullPath.substringAfterLast("/")
//val fileName = fullName.substringBeforeLast(".")
//val extension = fullName.substringAfterLast(".")

fun String.hasFileExtension(extension: String): Boolean {
    val sub =  kotlin.runCatching { this.substring(this.lastIndexOf(".")) }.getOrNull() ?: ""
    return extension == sub.replace(".", "")
}


fun String.getFileName(): String {
    return  this.substringAfterLast("/").substringBeforeLast(".")
}

fun String.getFullFileName(): String {
    return this.substringAfterLast("/")
}


/** Culculate size space specific  folder. */

fun File.getFolderSize(): Long {
    var size: Long = 0
    for (file in this.listFiles()) {
        if (file.isFile()) { // System.out.println(file.getName() + " " + file.length());
            size += file.length()
        } else size += file.getFolderSize()
    }
    return size
}


fun File.safetyCreateParentFolders() {
    if (!this.getParentFile().exists()) {
        this.getParentFile().mkdirs()
    }
}

/** Copy Resource file to internal storage. */

fun Context.copyFiletoInternalStorage(resourceId: Int, resourceName: String): String {

    val pathFile = this.filesDir.absolutePath + "/" + resourceName
    val `in` = getResources().openRawResource(resourceId)
    var out: FileOutputStream? = null
    out = FileOutputStream(pathFile)
    val buff = ByteArray(1024)
    var read = 0
    try {
        read = `in`.read(buff)

        while (read > 0) {
            out.write(buff, 0, read)
            read = `in`.read(buff)
        }
    } finally {
        `in`.close()
        out.close()
    }



    return pathFile

}


fun File.toBitmap(): Bitmap {

    return GlideBitmapFactory.decodeFile(this.absolutePath);
//
}


/**
 * @param path the path to the Video
 * @return a thumbnail of the video or null if retrieving the thumbnail failed.
 */
fun File.getVidioThumbnail(): Bitmap? {

    val path: String = this.absolutePath

    var bitmap: Bitmap? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
        bitmap =
            ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND)
        if (bitmap != null) {
            return bitmap
        }
    }

    return bitmap
}


fun File.getVideoDurationMillis(context: Context): Long {

    if (!exists()) return 0
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(context, Uri.parse(absolutePath))
    val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
    retriever.release()

    return duration.toLongOrNull() ?: 0

}