package sample.WDTComponents.DelegateMethods

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

interface IDelegateMethodSocketAction {
    fun voidMethod(
        string: String,
        string2: String,
        dataInputStream: DataInputStream,
        dataOutputStream: DataOutputStream,
        socket: Socket
    )
}