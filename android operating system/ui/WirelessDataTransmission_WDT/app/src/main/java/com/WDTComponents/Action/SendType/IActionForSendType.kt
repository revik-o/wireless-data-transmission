package com.WDTComponents.Action.SendType

import com.WDTComponents.DelegateMethods.IDelegateMethod

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.net.Socket

/**
 *
 */
interface IActionForSendType {

    /**
     *
     */
    fun clientActionForSendType1(socket: Socket, files: List<File>)

    fun clientActionForSendType2(socket: Socket, files: List<File>)

    fun clientActionForSendType3(socket: Socket)

    /**
     *
     */
    fun serverActionForSendType1(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, clientNameDevice: String, clientDeviceType: String, endMethod: IDelegateMethod)

    fun serverActionForSendType2(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, clientNameDevice: String, clientDeviceType: String, endMethod: IDelegateMethod)

    fun serverActionForSendType3(dataOutputStream: DataOutputStream, endMethod: IDelegateMethod)

}