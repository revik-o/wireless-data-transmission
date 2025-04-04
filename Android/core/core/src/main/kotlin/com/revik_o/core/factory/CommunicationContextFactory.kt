package com.revik_o.core.factory

import com.revik_o.core.AppVersion
import com.revik_o.core.context.ClipboardCommunicationContextI
import com.revik_o.core.context.CommunicationContextI
import com.revik_o.core.context.FileCommunicationContextI
import com.revik_o.core.dto.DeviceInfoDto
import java.io.File

object CommunicationContextFactory {

    fun buildCommunicationContext(
        device: DeviceInfoDto,
        appVersion: AppVersion
    ): CommunicationContextI = object : CommunicationContextI {
        override val communicationDevice: DeviceInfoDto
            get() = device
        override val applicationVersion: AppVersion
            get() = appVersion
    }

    fun buildCommunicationContext(device: DeviceInfoDto): CommunicationContextI =
        object : CommunicationContextI {
            override val communicationDevice: DeviceInfoDto
                get() = device
        }

    fun buildCommunicationContext(
        device: DeviceInfoDto,
        vararg files: File
    ): FileCommunicationContextI = object : FileCommunicationContextI {
        override val data: Array<out File>
            get() = files
        override val communicationDevice: DeviceInfoDto
            get() = device
    }

    fun buildCommunicationContext(
        device: DeviceInfoDto,
        stringData: String
    ): ClipboardCommunicationContextI = object : ClipboardCommunicationContextI {
        override val data: String
            get() = stringData
        override val communicationDevice: DeviceInfoDto
            get() = device
    }
}