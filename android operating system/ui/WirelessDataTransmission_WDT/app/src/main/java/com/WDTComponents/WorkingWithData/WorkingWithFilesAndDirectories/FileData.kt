package com.WDTComponents.WorkingWithData.WorkingWithFilesAndDirectories

import com.WDTComponents.AppOption
import com.WDTComponents.DelegateMethods.IDelegateMethodDoubleArg
import java.io.InputStream
import java.io.OutputStream

/**
 *
 */
object FileData {

    /**
     *
     */
    fun writeFile(inputStream: InputStream, outputStream: OutputStream, lengthFile: Long, iDelegateMethodDoubleArg: IDelegateMethodDoubleArg) {
        var countOfWrite: Int
        val byteArray = ByteArray(AppOption.BUFFER_SIZE_FOR_TRANSFER)
        var length = 0
        while (inputStream.read(byteArray).also { countOfWrite = it } > 0) {
            outputStream.write(byteArray, 0, countOfWrite)
            iDelegateMethodDoubleArg.voidMethod(length.toDouble() / lengthFile)
        }
        outputStream.flush()
    }

    /**
     *
     */
    fun readFile(inputStream: InputStream, outputStream: OutputStream, lengthFile: Long, iDelegateMethodDoubleArg: IDelegateMethodDoubleArg) {
        var countOfWrite: Int
        var length = 0
        val byteArray = ByteArray(AppOption.BUFFER_SIZE_FOR_TRANSFER)
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