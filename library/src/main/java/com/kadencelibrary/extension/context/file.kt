package com.kadencelibrary.extension.context

import java.io.File


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
