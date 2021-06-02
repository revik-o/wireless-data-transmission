package com.WDTComponents.ArgClass

import java.io.InputStream

class FileInfo(
    fileName: String,
    filePath: String,
    fileSize: Long,
    inputStream: InputStream
) {

    val fileName: String = fileName
    val filePath: String = filePath
    val fileSize: Long = fileSize
    val inputStream: InputStream = inputStream

}