package com.WDTlib.WorkingWithData.WorkingWithFilesAndDirectories

import com.WDTlib.AppOption
import com.WDTlib.DelegateMethods.IDelegateMethodDoubleArg

import java.io.InputStream
import java.io.OutputStream

/**
 *
 */
object FileData {

    /**
     *
     */
    fun writeFile(inputStream: InputStream, outputStream: OutputStream) {
        var countOfWrite: Int
        val byteArray = ByteArray(AppOption.Option.BUFFER_SIZE_FOR_TRANSFER)
        while (inputStream.read(byteArray).also { countOfWrite = it } > 0) outputStream.write(byteArray, 0, countOfWrite)
        outputStream.flush()
    }

    /**
     *
     */
    fun readFile(inputStream: InputStream, outputStream: OutputStream, lengthFile: Long, iDelegateMethodDoubleArg: IDelegateMethodDoubleArg) {
        var countOfWrite: Int
        var length = 0
        val byteArray = ByteArray(AppOption.Option.BUFFER_SIZE_FOR_TRANSFER)
        while (inputStream.read(byteArray).also { countOfWrite = it } > 0) {
            length += countOfWrite
            if (length >= lengthFile) {
                outputStream.write(byteArray, 0, countOfWrite)
                iDelegateMethodDoubleArg.voidMethod(length.toDouble() / lengthFile)
                break
            }
            outputStream.write(byteArray, 0, countOfWrite)
            iDelegateMethodDoubleArg.voidMethod(length.toDouble() / lengthFile)
        }
        outputStream.flush()
    }

}