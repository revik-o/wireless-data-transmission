package com.revik_o.infrastructure.common

import com.revik_o.infrastructure.common.commands.send.ClipboardCommand
import com.revik_o.infrastructure.common.commands.send.ResourcesCommand

interface CommunicationProtocolSenderI {
    suspend fun send(command: ResourcesCommand)
    suspend fun send(command: ClipboardCommand)
}