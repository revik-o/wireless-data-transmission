package com.WDTlib.Action.SendType

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
    fun clientActionForSendType1(socket: Socket, fileSet: Set<File>)

    fun clientActionForSendType2(socket: Socket, fileSet: Set<File>)

    fun clientActionForSendType3(socket: Socket)

    /**
     *
     */
    fun serverActionForSendType1(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, clientNameDevice: String, clientDeviceType: String)

    fun serverActionForSendType2(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, clientNameDevice: String, clientDeviceType: String)

    fun serverActionForSendType3(dataOutputStream: DataOutputStream)

}