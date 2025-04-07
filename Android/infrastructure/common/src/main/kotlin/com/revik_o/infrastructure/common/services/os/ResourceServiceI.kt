package com.revik_o.infrastructure.common.services.os

import java.io.InputStream
import java.io.OutputStream

interface ResourceServiceI {
    fun writeResourceData(from: String, to: OutputStream): Boolean
    fun readResourceData(from: InputStream, to: OutputStream, expectedLength: Long): Boolean
    fun scanDirectories(
        onResource: (String, Long) -> Unit = { _, _ -> },
        onDir: (String) -> Unit,
        vararg resources: String
    )
}