package com.revik_o.infrastructure.tcp

import com.revik_o.core.common.exceptions.DataTransferFailedException
import com.revik_o.infrastructure.common.CommunicationProtocolSenderI
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.commands.send.ClipboardCommand
import com.revik_o.infrastructure.common.commands.send.ResourcesCommand
import com.revik_o.infrastructure.tcp.TCPAppCodes.DECLINED_STATUS
import com.revik_o.infrastructure.tcp.TCPAppCodes.OK_STATUS
import com.revik_o.infrastructure.tcp.exception.BrokenRequestException
import com.revik_o.infrastructure.tcp.handler.TCPDataHandler.Companion.SPLITTER

class TCPSender(private val _osAPI: OSAPIInterface) : CommunicationProtocolSenderI {

    override suspend fun send(command: ResourcesCommand) {
        createEstablishedTCPConnection(command.ip, _osAPI, command) { handler ->
            var resources = 0
            var dirs = 0

            _osAPI.resourceService.scanDirectories(
                onResource = { _, _ -> resources++ },
                onDir = { dirs++ },
                *command.paths
            )

            handler.send("$resources,$dirs")

            when (handler.readInt()) {
                OK_STATUS -> {
                    _osAPI.resourceService.scanDirectories(
                        onResource = { path, length ->
                            handler.send("RESOURCE$SPLITTER$path$SPLITTER$length")
                            handler.sendResource(path)

                            if (handler.readInt() < 0) {
                                throw DataTransferFailedException()
                            }
                        },
                        onDir = { path ->
                            handler.send("DIR${SPLITTER}$path")

                            if (handler.readInt() < 0) {
                                throw DataTransferFailedException()
                            }
                        },
                        *command.paths
                    )
                    handler.send("EOS")

                    if (handler.readInt() < 0) {
                        throw DataTransferFailedException()
                    }
                }

                DECLINED_STATUS -> throw BrokenRequestException()
            }
        }
    }

    override suspend fun send(command: ClipboardCommand) {
        createEstablishedTCPConnection(command.ip, _osAPI, command) { handler ->
            when (handler.readInt()) {
                OK_STATUS -> {
                    val textData = _osAPI.clipboardService.clipboardTextData

                    if (textData != null) {
                        handler.send(OK_STATUS)
                        handler.send(textData)
                    } else {
                        handler.send(DECLINED_STATUS)
                    }

                    if (handler.readInt() < 0) {
                        throw DataTransferFailedException()
                    }
                }

                DECLINED_STATUS -> throw DataTransferFailedException()
            }
        }
    }
}