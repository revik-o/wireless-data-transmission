package sample.lib

import sample.DataBase.Model.DeviceModel
import sample.lib.DelegateFunction.IDelegateFunction4Action
import sample.lib.DeviceIP.enumerableIp4
import sample.lib.DeviceWork.ScanDevices
import sample.lib.DeviceWork.sendData
import sample.lib.FileWork.sendDataFromDirectory
import sample.lib.FileWork.sendDataFromFile
import sample.lib.Message.ILoadStageMessage
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object DATABASE_ACTION {

    fun addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress: String, nameDevice: String, typeDevice: String) {
        println(internetProtocolAddress)
        println(nameDevice)
        var isIpExistsInDatabase = false
        for (strings in publicDeviceModelDAO.selectWhereIPLike(enumerableIp4(internetProtocolAddress)))
            if (strings[3].equals(internetProtocolAddress)) {
                isIpExistsInDatabase = true
                break
            }

        if (!isIpExistsInDatabase) {
            publicDeviceModelDAO.insert(DeviceModel(nameDevice, typeDevice, internetProtocolAddress))
            println("add new device to database")
        }
    }

}

object BUTTON_ACTION {

    fun sendButtonAction(scanDevices: ScanDevices, socket: Socket, nameDevice: String, typeDevice: String, fileSet: Set<File>) {
        val internetProtocolAddress: String = socket.inetAddress.toString().substring(1)
        scanDevices.stopScan()
        DATABASE_ACTION.addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress, nameDevice, typeDevice)
        if (fileSet.isNotEmpty()) {
            var sendType = 0
            for (file in fileSet){
                if (file.isDirectory) {
                    sendType = 2
                    break
                }
                else sendType = 1
            }
            if (sendType == 1) { // Send Files
                sendData(
                        InetSocketAddress(internetProtocolAddress, socket.port), object: IDelegateFunction4Action {
                            override fun voidFunction(
                              nameDevice: String, typeDevice: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, socket: Socket
                            ) {
                                try {
                                    dataOutputStream.write(sendType)
                                    dataOutputStream.write(fileSet.size)
                                    val iLoadStageMessage: ILoadStageMessage = publicILoadStageMessage.getConstructor().newInstance()
                                    iLoadStageMessage.showMessage()
                                    fileSet.forEach { file -> sendDataFromFile(file, dataOutputStream, dataInputStream, iLoadStageMessage)}
                                    iLoadStageMessage.closeMessage()
                                }
                                catch (E: IOException) { E.printStackTrace() }
                            }
                        }
                )
            } else if (sendType == 2) { // Send Directories & Files
                sendData(
                        InetSocketAddress(internetProtocolAddress, socket.port), object: IDelegateFunction4Action {
                            override fun voidFunction(
                              nameDevice: String, typeDevice: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, socket: Socket
                            ) {
                                try {
                                    dataOutputStream.write(sendType)
                                    dataOutputStream.write(fileSet.size)
                                    val iLoadStageMessage: ILoadStageMessage = publicILoadStageMessage.getConstructor().newInstance()
                                    iLoadStageMessage.showMessage()
                                    fileSet.forEach {
                                        if (it.isDirectory) {
                                            try {
                                                dataOutputStream.writeBoolean(true)
                                                sendDataFromDirectory(it, it, dataInputStream, dataOutputStream, iLoadStageMessage)
                                            }
                                            catch (E: IOException) { println("Не удалось отправить directories") }
                                        } else {
                                            try {
                                                dataOutputStream.writeBoolean(false)
                                                sendDataFromFile(it, dataOutputStream, dataInputStream, iLoadStageMessage)
                                            }
                                            catch (E: IOException) { println("Не удалось отправить файл") }
                                        }
                                    }
                                    iLoadStageMessage.closeMessage()
                                }
                                catch (E: IOException) { E.printStackTrace() }
                            }
                        }
                )
            }
        }
        else { println("empty") }
        println("всё отправленно")
    }

}