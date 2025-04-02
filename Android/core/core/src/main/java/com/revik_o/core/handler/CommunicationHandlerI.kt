package com.revik_o.core.handler

interface CommunicationHandlerI {

    fun send(data: String)
    fun send(data: Long)
    fun send(data: Int)
    fun send(data: Byte): Any
    fun readString(): String
    fun readLong(): Long
    fun readInt(): Int
    fun readByte(): Byte
}