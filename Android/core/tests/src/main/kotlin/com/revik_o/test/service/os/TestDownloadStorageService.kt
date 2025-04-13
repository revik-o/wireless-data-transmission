package com.revik_o.test.service.os

import com.revik_o.infrastructure.common.dtos.RemoteResourceData
import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import com.revik_o.test.exceptions.UnexpectedTestBehaviour
import com.revik_o.test.utils.LogUtils
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

const val TEST_OUTPUT_DIR = "./src/test/resources/.downloads"

class TestDownloadStorageService : DownloadStorageServiceI {

    init {
        File(TEST_OUTPUT_DIR).also { ref ->
            if (ref.exists()) {
                ref.deleteRecursively()
            }
        }.mkdirs()
    }

    override fun createResourceOutputStream(resource: RemoteResourceData): OutputStream? {
        val dirRef = File(TEST_OUTPUT_DIR, resource.dirSequence)

        if (!dirRef.exists() && !dirRef.mkdirs()) {
            return null
        }

        val ptr = File(TEST_OUTPUT_DIR, "${resource.dirSequence}${resource.fileName}")

        if (!ptr.exists() && !ptr.createNewFile()) {
            throw UnexpectedTestBehaviour("Cannot create new file")
        }

        LogUtils.debug("created $TEST_OUTPUT_DIR/${resource.dirSequence}${resource.fileName}")

        return TestOutputStream(ptr, resource.size)
    }
}

private class TestOutputStream(private val _file: File, private val _length: Long) :
    FileOutputStream(_file) {

    private var _sentBytes = 0L

    override fun write(p0: ByteArray, p1: Int, p2: Int) {
        super.write(p0, p1, p2)
        _sentBytes += p2
        LogUtils.debug("${_file.path} -> \u001B[32m${(_sentBytes.toDouble() / _length) * 100}%\u001B[0m")
    }
}