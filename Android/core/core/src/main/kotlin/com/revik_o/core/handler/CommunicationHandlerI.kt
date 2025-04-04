package com.revik_o.core.handler

import java.io.File

interface CommunicationHandlerI {

    fun send(data: String)
    fun send(data: Long)
    fun send(data: Int)
    fun send(resource: File, progress: (UShort) -> Unit = {})
    fun readString(): String
    fun readLong(): Long
    fun readInt(): Int
    fun readResource(resourcePtr: File, expectedResourceLength: ULong, progress: (UShort) -> Unit)
}