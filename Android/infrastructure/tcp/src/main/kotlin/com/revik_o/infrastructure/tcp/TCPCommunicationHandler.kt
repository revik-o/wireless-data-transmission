package com.revik_o.infrastructure.tcp

import com.revik_o.core.handler.CommunicationHandlerI
import com.revik_o.infrastructure.resource.ResourceUtils
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.Socket

class TCPCommunicationHandler(socket: Socket) : CommunicationHandlerI {

    private val _inputStream = DataInputStream(BufferedInputStream(socket.getInputStream()))
    private val _outputStream = DataOutputStream(socket.getOutputStream())

    override fun send(data: String) = _outputStream.writeUTF(data)

    override fun send(data: Long) = _outputStream.writeLong(data)

    override fun send(data: Int) = _outputStream.writeInt(data)

    override fun send(resource: File, progress: (UShort) -> Unit) = ResourceUtils.writeInto(
        FileInputStream(resource),
        _outputStream,
        resource.length().toULong(),
        progress
    )

    override fun readString(): String = _inputStream.readUTF()

    override fun readLong(): Long = _inputStream.readLong()

    override fun readInt(): Int = _inputStream.readInt()

    override fun readResource(
        resourcePtr: File,
        expectedResourceLength: ULong,
        progress: (UShort) -> Unit
    ) = ResourceUtils.readFrom(
        _inputStream,
        FileOutputStream(resourcePtr),
        expectedResourceLength,
        progress
    )
}