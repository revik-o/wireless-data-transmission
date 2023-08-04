package com.WDTComponents.DelegateMethods

import com.WDTComponents.AlertInterfaces.ILoadAlert
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

interface IDelegateMethodSocketActionWithLoadAlert {
    fun voidMethod(
        loadAlert: ILoadAlert,
        nameDevice: String,
        typeDevice: String,
        ipStaring: String,
        dataInputStream: DataInputStream,
        dataOutputStream: DataOutputStream,
        socket: Socket
    )
}