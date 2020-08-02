package com.kadencelibrary.utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutput
import java.io.ObjectOutputStream

object ObjectSizeFetcher {

    fun getSize(o: Any ): Int {
        return getBytes(o)?.size ?: -1

    }

    private fun getBytes(o: Any): ByteArray? {
        val bos = ByteArrayOutputStream()
        var out: ObjectOutput? = null
        try {
            out = ObjectOutputStream(bos)
            out.writeObject(o)
            return bos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (ex: IOException) {
                // ignore close exception
            }
            try {
                bos.close()
            } catch (ex: IOException) {
                // ignore close exception
            }
        }
        return null
    }
}