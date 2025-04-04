package com.revik_o.core.service

import com.revik_o.core.context.ClipboardCommunicationContextI
import com.revik_o.core.context.CommunicationContextI
import com.revik_o.core.context.FileCommunicationContextI
import com.revik_o.core.dto.RemoteDeviceDto
import com.revik_o.core.handler.CommunicationHandlerI

interface DeviceCommunicationServiceI {

    fun send(communicationContext: ClipboardCommunicationContextI, then: (Boolean) -> Unit = {})

    fun send(
        communicationContext: FileCommunicationContextI,
        onSending: (UShort, String) -> Unit = { _, _ -> },
        then: (String?) -> Unit = {}
    )

    fun send(communicationContext: CommunicationContextI, then: (RemoteDeviceDto) -> Unit)

    fun accept(communicationHandler: CommunicationHandlerI)
}