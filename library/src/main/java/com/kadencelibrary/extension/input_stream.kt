package com.kadencelibrary.extension

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

public fun InputStream.copyTo(
    out: OutputStream,
    skip: Long,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
): Long {
    var bytesCopied: Long = 0
    val buffer = ByteArray(bufferSize)
    skip(skip)
    var bytes = read(buffer)
    while (bytes >= 0) {
        out.write(buffer, 0, bytes)
        bytesCopied += bytes
        bytes = read(buffer)
    }
    return bytesCopied
}

public fun InputStream.readBytes(skip: Long): ByteArray {
    val buffer = ByteArrayOutputStream(maxOf(DEFAULT_BUFFER_SIZE, this.available()))
    copyTo(buffer, skip)
    return buffer.toByteArray()
}