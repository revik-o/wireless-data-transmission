package com.revik_o.impl.service

import com.revik_o.infrastructure.common.services.os.ResourceServiceI
import java.io.InputStream
import java.io.OutputStream

class ResourceService : ResourceServiceI {

    override fun writeResourceData(from: String, to: OutputStream): Boolean {
        return false
    }

    override fun readResourceData(
        from: InputStream,
        to: OutputStream,
        expectedLength: Long
    ): Boolean {
        return false
    }

    override fun scanDirectories(
        onResource: (String, Long) -> Unit,
        onDir: (String) -> Unit,
        vararg resources: String
    ) {
        TODO("Not yet implemented")
    }
}