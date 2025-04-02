package com.revik_o.infrastructure.tcp

import com.revik_o.core.handler.CommunicationHandlerI
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class TCPCommunicationHandler(private val _socket: Socket): CommunicationHandlerI {

    private val _inputStream = DataInputStream(BufferedInputStream(_socket.getInputStream()))
    private val _outputStream = DataOutputStream(_socket.getOutputStream())

    override fun send(data: String) = _outputStream.writeUTF(data)

    override fun send(data: Long) = _outputStream.writeLong(data)

    override fun send(data: Int) = _outputStream.writeInt(data)

    override fun send(data: Byte): Any {
        TODO("Not yet implemented")
    }

    override fun readString(): String = _inputStream.readUTF()

    override fun readLong(): Long = _inputStream.readLong()

    override fun readInt(): Int = _inputStream.readInt()

    override fun readByte(): Byte {
        TODO("Not yet implemented")
    }
}