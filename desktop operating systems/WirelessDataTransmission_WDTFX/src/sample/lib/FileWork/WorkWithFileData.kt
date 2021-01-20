package sample.lib.FileWork

import java.io.InputStream
import java.io.OutputStream

fun sendFile(inputStream: InputStream, outputStream: OutputStream) {
    var countOfWrite: Int
    val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)
    while (inputStream.read(byteArray).also { countOfWrite = it } > 0) outputStream.write(byteArray, 0, countOfWrite)
    outputStream.flush()
}

fun acceptFile(inputStream: InputStream, outputStream: OutputStream, lengthFile: Long) {
    var countOfWrite: Int
    var length = 0
    val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)
    while (inputStream.read(byteArray).also { countOfWrite = it } > 0) {
        length += countOfWrite
        if (length >= lengthFile) {
            outputStream.write(byteArray, 0, countOfWrite)
            break
        }
        outputStream.write(byteArray, 0, countOfWrite)
    }
    outputStream.flush()
}