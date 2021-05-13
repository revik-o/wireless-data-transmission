package com.WDTComponents.DelegateMethods

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

interface IDelegateMethodSocketAction {
    fun voidMethod(
        nameDevice: String,
        typeDevice: String,
        dataInputStream: DataInputStream,
        dataOutputStream: DataOutputStream,
        socket: Socket
    )
}