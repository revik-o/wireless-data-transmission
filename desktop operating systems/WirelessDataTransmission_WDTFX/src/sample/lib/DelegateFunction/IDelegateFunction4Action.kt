package sample.lib.DelegateFunction

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

interface IDelegateFunction4Action{
    fun voidFunction(
            string1: String,
            string2: String,
            dataInputStream: DataInputStream,
            dataOutputStream: DataOutputStream,
            socket: Socket
    )
}