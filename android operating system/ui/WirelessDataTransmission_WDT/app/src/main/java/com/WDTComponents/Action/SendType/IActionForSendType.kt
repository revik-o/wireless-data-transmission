package com.WDTComponents.Action.SendType

import com.WDTComponents.ArgClass.FileInfo
import com.WDTComponents.DataBase.Model.FileModel
import com.WDTComponents.DelegateMethods.IDelegateMethod

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.InputStream
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

    fun clientActionForSendType4(socket: Socket)

    fun clientActionForSendType5(socket: Socket, fileInfoList: List<FileInfo>)

    /**
     *
     */
    fun serverActionForSendType1(
            dataInputStream: DataInputStream,
            dataOutputStream: DataOutputStream,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            endMethod: IDelegateMethod
    )

    fun serverActionForSendType2(
            dataInputStream: DataInputStream,
            dataOutputStream: DataOutputStream,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            endMethod: IDelegateMethod
    )

    fun serverActionForSendType3(
            dataOutputStream: DataOutputStream,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            endMethod: IDelegateMethod
    )

    fun serverActionForSendType4(
            dataInputStream: DataInputStream,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            endMethod: IDelegateMethod
    )

}