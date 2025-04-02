package com.revik_o.core.service

import com.revik_o.core.context.ClipboardCommunicationContextI
import com.revik_o.core.context.CommunicationContextI
import com.revik_o.core.context.FileCommunicationContextI
import com.revik_o.core.dto.RemoteDeviceDto
import com.revik_o.core.handler.CommunicationHandlerI

interface DeviceCommunicationServiceI {

    fun send(communicationContext: ClipboardCommunicationContextI)

    fun send(communicationContext: FileCommunicationContextI)

    fun send(communicationContext: CommunicationContextI, then: (RemoteDeviceDto) -> Unit)

    fun accept(communicationHandler: CommunicationHandlerI)
}