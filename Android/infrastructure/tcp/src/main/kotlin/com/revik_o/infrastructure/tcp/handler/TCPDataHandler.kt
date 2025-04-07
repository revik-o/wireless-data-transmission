package com.revik_o.infrastructure.tcp.handler

import com.revik_o.core.common.AppVersion
import com.revik_o.core.common.OSType
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.commands.NetworkCommandI
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.APP_VERSION_KEY
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.OS_KEY
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto.CurrentDeviceDto.Companion.TITLE_KEY
import com.revik_o.infrastructure.tcp.dto.TCPRemoteDeviceDto
import com.revik_o.infrastructure.tcp.dto.TCPRemoteDeviceDto.Companion.buildRemoteDeviceDto
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class TCPDataHandler(socket: Socket, private val _osApi: OSAPIInterface) {

    private val _inputStream = DataInputStream(BufferedInputStream(socket.getInputStream()))
    private val _outputStream = DataOutputStream(socket.getOutputStream())

    fun send(data: String) = _outputStream.writeUTF(data)

    fun send(data: Long) = _outputStream.writeLong(data)

    fun send(data: Int) = _outputStream.writeInt(data)

    fun send(data: CurrentDeviceDto) =
        send("${data.os.signature}$SPLITTER${data.appVersion.name}$SPLITTER${data.title}")

    fun sendResource(path: String) =
        _osApi.resourceService.writeResourceData(path, _outputStream)

    fun intent(data: CurrentDeviceDto, command: NetworkCommandI) =
        send(
            "${command.requestType.signature}$SPLITTER" +
                    "${data.os.signature}$SPLITTER" +
                    "${command.fetchOrSendType.signature}$SPLITTER" +
                    "${data.appVersion.name}$SPLITTER" +
                    data.title
        )

    fun readString(): String = _inputStream.readUTF()

    fun readLong(): Long = _inputStream.readLong()

    fun readInt(): Int = _inputStream.readInt()

    fun readRemoteDeviceData(): Array<OptionalData<*>> = readString().split(SPLITTER).let {
        arrayOf(
            OptionalData(OS_KEY, OSType.getOSBySignature(it[0].toShort())),
            OptionalData(APP_VERSION_KEY, AppVersion.getAppVersionBySignature(it[1])),
            OptionalData(TITLE_KEY, it[2]),
        )
    }


    fun readResource(from: OutputStream, expectedLength: Long) =
        _osApi.resourceService.readResourceData(_inputStream, from, expectedLength)

    fun readIntent(): TCPRemoteDeviceDto? = buildRemoteDeviceDto(readString())

    data class OptionalData<T>(val key: String, val valRef: T?)

    companion object {
        const val SPLITTER = "%/%"

        fun <T> Array<OptionalData<*>>.extractData(
            key: String,
            type: Class<T>
        ): T? {
            for (arg in iterator()) {
                if (arg.key == key) {
                    return type.cast(arg.valRef)
                }
            }

            return null
        }
    }
}