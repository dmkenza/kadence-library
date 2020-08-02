package com.kadencelibrary.utils

import android.media.MediaMetadataRetriever


class SizeFromVideoFileUtil(private val filePath: String) {

    val retriever = MediaMetadataRetriever()

    data class Size(val height : Int , val width : Int   )


    init {
        retriever.setDataSource(filePath)

    }


    private  fun widthTrue(): Int {
       return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).toInt()
    }

    private fun heightTrue(): Int {
        return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).toInt()
    }


    fun width(): Int {

        val rotation: String = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)

        if(rotation == "90" || rotation == "270"){
            return heightTrue()
        }else{
            return widthTrue()
        }

    }

    fun height(): Int {
        val rotation: String =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)

        if (rotation == "90" || rotation == "270" ) {
            return widthTrue()
        } else {
            return heightTrue()
        }

    }

    fun getSize(): Size {

        return  Size(height(), width() )

    }


}