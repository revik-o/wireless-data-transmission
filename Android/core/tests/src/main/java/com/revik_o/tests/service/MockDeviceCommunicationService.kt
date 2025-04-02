package com.revik_o.tests.service

import com.revik_o.core.context.ClipboardCommunicationContextI
import com.revik_o.core.context.CommunicationContextI
import com.revik_o.core.context.FileCommunicationContextI
import com.revik_o.core.dto.RemoteDeviceDto
import com.revik_o.core.handler.CommunicationHandlerI
import com.revik_o.core.service.DeviceCommunicationServiceI

data class MockDeviceCommunicationService(
    private val sendClipboard: (ClipboardCommunicationContextI) -> Unit = {},
    private val sendFile: (FileCommunicationContextI) -> Unit = {},
    private val sendPing: (CommunicationContextI, (RemoteDeviceDto) -> Unit) -> Unit = { _, _ -> },
    private val acceptFunc: (CommunicationHandlerI) -> Unit = {},
) : DeviceCommunicationServiceI {

    override fun send(communicationContext: ClipboardCommunicationContextI) =
        sendClipboard(communicationContext)

    override fun send(communicationContext: FileCommunicationContextI) =
        sendFile(communicationContext)

    override fun send(
        communicationContext: CommunicationContextI,
        then: (RemoteDeviceDto) -> Unit
    ) = sendPing(communicationContext, then)

    override fun accept(communicationHandler: CommunicationHandlerI) =
        acceptFunc(communicationHandler)
}