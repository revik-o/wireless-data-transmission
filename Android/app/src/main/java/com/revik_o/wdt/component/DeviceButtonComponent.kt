package com.revik_o.wdt.component

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.AppCompatButton
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto

@SuppressLint("ViewConstructor")
class DeviceButtonComponent(
    context: Context,
    device: RemoteDeviceDto,
    onClick: (RemoteDeviceDto) -> Unit
) :
    AppCompatButton(context) {

    init {
        text = device.title
        setOnClickListener { onClick(device) }
    }
}