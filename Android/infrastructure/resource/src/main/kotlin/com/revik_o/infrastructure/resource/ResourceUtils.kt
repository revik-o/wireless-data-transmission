package com.revik_o.infrastructure.resource

import java.io.File
import java.io.InputStream
import java.io.OutputStream

object ResourceUtils {

    private const val BUFFER_SIZE_FOR_TRANSFER = 4096

    fun writeInto(
        resource: InputStream,
        destination: OutputStream,
        expectedResourceLength: ULong,
        progress: (UShort) -> Unit
    ) {
        var countOfWrite: Int
        var length = 0uL
        val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)

        while (resource.read(byteArray).also { countOfWrite = it } > 0) {
            length += countOfWrite.toULong()
            destination.write(byteArray, 0, countOfWrite)
            progress(((length.toUInt() / expectedResourceLength) * 100u).toUShort())
        }

        destination.flush()
    }

    fun readFrom(
        resource: InputStream,
        destination: OutputStream,
        expectedResourceLength: ULong,
        progress: (UShort) -> Unit
    ) {
        var countOfWrite: Int
        var length = 0uL
        val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)

        while (resource.read(byteArray).also { countOfWrite = it } > 0) {
            length += countOfWrite.toULong()

            if (length >= expectedResourceLength) {
                destination.write(byteArray, 0, countOfWrite)
                progress(((length.toUInt() / expectedResourceLength) * 100u).toUShort())
                break
            }

            destination.write(byteArray, 0, countOfWrite)
            progress(((length.toUInt() / expectedResourceLength) * 100u).toUShort())
        }

        destination.flush()
    }

    fun scanResources(onFile: (File) -> Unit = {}, onFolder: (File) -> Unit, vararg resources: File) {
        for (item in resources) {
            if (item.isDirectory) {
                item.listFiles()?.let { files ->
                    onFolder(item)
                    scanResources(onFile, onFolder, *files)
                }
            } else {
                onFile(item)
            }
        }
    }
}