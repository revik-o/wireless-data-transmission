package com.revik_o.test.service.os

import com.revik_o.infrastructure.common.services.os.ResourceServiceI
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream

private const val BUFFER_SIZE_FOR_TRANSFER = 4096

class TestResourceService : ResourceServiceI {

    override fun writeResourceData(from: String, to: OutputStream): Boolean {
        var countOfWrite: Int
        val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)
        val inputStream = FileInputStream(File(from))

        while (inputStream.read(byteArray).also { countOfWrite = it } > 0) {
            to.write(byteArray, 0, countOfWrite)
        }

        to.flush()
        return true
    }

    override fun readResourceData(
        from: InputStream,
        to: OutputStream,
        expectedLength: Long
    ): Boolean {
        var length = 0
        var countOfWrite: Int
        val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)

        while (from.read(byteArray).also { countOfWrite = it } > 0) {
            length += countOfWrite

            if (length >= expectedLength) {
                to.write(byteArray, 0, countOfWrite)
                break
            }

            to.write(byteArray, 0, countOfWrite)
        }

        to.flush()
        return true
    }

    override fun scanDirectories(
        onResource: (String, Long) -> Unit,
        onDir: (String) -> Unit,
        vararg resources: String
    ) {
        for (item in resources) {
            val file = File(item)
            if (file.isDirectory) {
                file.listFiles()?.let { files ->
                    onDir(file.path)
                    scanDirectories(onResource, onDir, *files.map { it.path }.toTypedArray())
                }
            } else {
                onResource(file.path, file.length())
            }
        }
    }
}