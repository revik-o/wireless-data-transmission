package com.revik_o.infrastructure.common

import com.revik_o.infrastructure.common.commands.send.ClipboardCommand
import com.revik_o.infrastructure.common.commands.send.ResourcesCommand

interface CommunicationProtocolSenderI<R> {
    suspend fun send(command: ResourcesCommand<R>)
    suspend fun send(command: ClipboardCommand)
}