package com.revik_o.test.service.os

import com.revik_o.infrastructure.common.dtos.ResourceData
import com.revik_o.infrastructure.common.services.os.ResourceServiceI
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream

private const val BUFFER_SIZE_FOR_TRANSFER = 4096

class TestResourceService : ResourceServiceI<File> {

    override fun writeResourceData(from: File, to: OutputStream): Boolean {
        var countOfWrite: Int
        val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)
        val inputStream = FileInputStream(from)

        while (inputStream.read(byteArray).also { countOfWrite = it } > 0) {
            to.write(byteArray, 0, countOfWrite)
        }

        to.flush()
        return true
    }

    override fun readResourceData(
        from: InputStream,
        to: OutputStream,
        expectedLength: Long,
        check: () -> Unit
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
        check()
        to.close()
        return true
    }

    override fun getResourcesFromRefs(vararg refs: File): Array<ResourceData<File>> {
        val files = ArrayList<ResourceData<File>>()
        scanDirectories({ ref, size -> files.add(ResourceData(ref.path, size, ref)) }, *refs)
        return files.toTypedArray()
    }

    fun scanDirectories(
        onResource: (File, Long) -> Unit,
        vararg resources: File
    ) {
        for (file in resources) {
            if (file.isDirectory) {
                file.listFiles()?.let { files ->
                    scanDirectories(onResource, *files)
                }
            } else {
                onResource(file, file.length())
            }
        }
    }
}