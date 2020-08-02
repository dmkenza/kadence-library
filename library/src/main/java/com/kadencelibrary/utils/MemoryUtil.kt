package com.kadencelibrary.utils


object MemoryUtil {

    /**
     * convert bytes count to formatted text
     */
    fun formatedSize(size: Long): String {
        var size = size
        var suffix: String? = null

        if (size >= 1024) {
            suffix = " KB "
            size /= 1024
            if (size >= 1024) {
                suffix = " MB"
                size /= 1024

                if (size >= 1024) {
                    suffix = " GB "
                    size /= 1024
                }
            }
        }

        val resultBuffer = StringBuilder(java.lang.Long.toString(size))

        var commaOffset = resultBuffer.length - 3
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',')
            commaOffset -= 3
        }

        if (suffix != null) resultBuffer.append(suffix)
        return resultBuffer.toString()
    }
}

