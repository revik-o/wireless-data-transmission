package com.WDTComponents.SystemClipboard

import java.io.InputStream

class ClipboardFile {

    var nameFile: String = ""
    var lengthFile: Long = 0
    var inputStream: InputStream? = null

    fun setNameFile(nameFile: String) : ClipboardFile {
        this.nameFile = nameFile
        return this
    }

    fun setLengthFile(lengthFile: Long) : ClipboardFile {
        this.lengthFile = lengthFile
        return this
    }

    fun setInputStream(inputStream: InputStream) : ClipboardFile {
        this.inputStream = inputStream
        return this
    }

}