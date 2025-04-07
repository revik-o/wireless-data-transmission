package com.revik_o.infrastructure.common.handlers

import java.io.OutputStream

interface ResourceHandlerI {
    suspend fun makeDirectory(path: String): Boolean
    suspend fun createResourceOutputStream(path: String, expectedResourceLength: Long)
            : OutputStream?
}