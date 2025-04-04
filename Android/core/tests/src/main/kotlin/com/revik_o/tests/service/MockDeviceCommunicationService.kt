package com.revik_o.tests.service

import com.revik_o.core.context.ClipboardCommunicationContextI
import com.revik_o.core.context.CommunicationContextI
import com.revik_o.core.context.FileCommunicationContextI
import com.revik_o.core.dto.RemoteDeviceDto
import com.revik_o.core.handler.CommunicationHandlerI
import com.revik_o.core.service.DeviceCommunicationServiceI

data class MockDeviceCommunicationService(
    private val sendClipboard: (ClipboardCommunicationContextI, (Boolean) -> Unit) -> Unit = { _, _ -> },
    private val sendFile: (FileCommunicationContextI) -> Unit = { TODO("file") },
    private val sendPing: (CommunicationContextI, (RemoteDeviceDto) -> Unit) -> Unit = { _, _ -> },
    private val acceptFunc: (CommunicationHandlerI) -> Unit = {},
) : DeviceCommunicationServiceI {

    override fun send(
        communicationContext: ClipboardCommunicationContextI,
        then: (Boolean) -> Unit
    ) = sendClipboard(communicationContext, then)

    override fun send(
        communicationContext: FileCommunicationContextI, onSending: () -> Unit,
        then: (String?) -> Unit
    ) = sendFile(communicationContext)

    override fun send(
        communicationContext: CommunicationContextI,
        then: (RemoteDeviceDto) -> Unit
    ) = sendPing(communicationContext, then)

    override fun accept(communicationHandler: CommunicationHandlerI) =
        acceptFunc(communicationHandler)
}