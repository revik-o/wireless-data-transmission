package com.revik_o.infrastructure.tcp

import com.revik_o.core.common.exceptions.DataTransferFailedException
import com.revik_o.infrastructure.common.CommunicationProtocolSenderI
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.commands.send.ClipboardCommand
import com.revik_o.infrastructure.common.commands.send.ResourcesCommand
import com.revik_o.infrastructure.tcp.TCPAppCodes.DECLINED_STATUS
import com.revik_o.infrastructure.tcp.TCPAppCodes.OK_STATUS
import com.revik_o.infrastructure.tcp.TCPDataHandler.Companion.SPLITTER
import com.revik_o.infrastructure.tcp.exceptions.BrokenRequestException

class TCPSender<T>(private val _osAPI: OSAPIInterface<T>) : CommunicationProtocolSenderI<T> {

    override suspend fun send(command: ResourcesCommand<T>) {
        createEstablishedTCPConnection(command.ip, _osAPI, command) { handler ->
            val resources = _osAPI.resourceService.getResourcesFromRefs(*command.refs)
            handler.send("${resources.size}")

            when (handler.readInt()) {
                OK_STATUS -> {
                    for (resource in resources) {
                        handler.send("${resource.path}$SPLITTER${resource.size}")
                        handler.sendResource(resource.ref)

                        if (handler.readInt() < 0) {
                            throw DataTransferFailedException()
                        }
                    }

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