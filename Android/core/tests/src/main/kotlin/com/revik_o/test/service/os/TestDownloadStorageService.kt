package com.revik_o.test.service.os

import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import com.revik_o.test.exceptions.UnexpectedTestBehaviour
import com.revik_o.test.utils.LogUtils
import org.junit.Assert.assertFalse
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

const val TEST_OUTPUT_DIR = "./src/test/resources/.downloads"

class TestDownloadStorageService : DownloadStorageServiceI {

    private val _regularExpression = Regex("(/{2,})|(\\./)|(\\.\\./)")

    init {
        File(TEST_OUTPUT_DIR).also { ref ->
            if (ref.exists()) {
                ref.deleteRecursively()
            }
        }.mkdirs()
    }

    override fun mkDir(path: String): Boolean {
        val endDestination = path
            .replace(_regularExpression, "/")
            .replace(_regularExpression, "/")

        assertFalse(endDestination.contains("//"))
        assertFalse(endDestination.contains("///"))
        assertFalse(endDestination.contains("../"))
        assertFalse(endDestination.contains("./"))

        return File(TEST_OUTPUT_DIR, endDestination).mkdirs()
    }

    override fun getResourceOutputStream(path: String, length: Long): OutputStream {
        val endDestination = path
            .replace(_regularExpression, "/")
            .replace(_regularExpression, "/")

        assertFalse(endDestination.contains("//"))
        assertFalse(endDestination.contains("///"))
        assertFalse(endDestination.contains("../"))
        assertFalse(endDestination.contains("./"))

        val ptr = File(TEST_OUTPUT_DIR, endDestination)

        if (!ptr.exists() && !ptr.createNewFile()) {
            throw UnexpectedTestBehaviour("Cannot create new file")
        }

        LogUtils.debug("created $TEST_OUTPUT_DIR/$endDestination")

        return TestOutputStream(ptr, length)
    }
}

private class TestOutputStream(private val _file: File, private val _length: Long) :
    OutputStream() {

    private var _sentBytes = 0L
    private val _fileOutputStream = FileOutputStream(_file)

    override fun write(p0: Int) = _fileOutputStream.write(p0)

    override fun write(p0: ByteArray, p1: Int, p2: Int) {
        _fileOutputStream.write(p0, p1, p2)
        _sentBytes += p2
        LogUtils.debug("${_file.path} -> ${(_sentBytes.toDouble() / _length) * 100}")
    }
}