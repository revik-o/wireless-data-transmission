package com.WDTComponents.Action.SendType

import com.WDTComponents.AlertInterfaces.ILoadAlert
import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption
import com.WDTComponents.DelegateMethods.IDelegateMethod
import com.WDTComponents.DelegateMethods.IDelegateMethodDoubleArg
import com.WDTComponents.DelegateMethods.IDelegateMethodSocketAction
import com.WDTComponents.DelegateMethods.IDelegateMethodStringArg
import com.WDTComponents.WorkingWithData.DataTransfer
import com.WDTComponents.WorkingWithData.WorkingWithFilesAndDirectories.DataTransferFromDirectory
import com.WDTComponents.WorkingWithData.WorkingWithFilesAndDirectories.DataTransferFromFile

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/**
 *
 */
object ActionForSendType: IActionForSendType {

    private fun setTextForLoadAlert(iLoadAlert: ILoadAlert): IDelegateMethodStringArg {
        return object : IDelegateMethodStringArg {
            override fun voidMethod(text: String) { iLoadAlert.setContentText(text) }
        }
    }

    private fun setPercentageOfDownloadForLoadAlert(iLoadAlert: ILoadAlert): IDelegateMethodDoubleArg {
        return object : IDelegateMethodDoubleArg {
            override fun voidMethod(double: Double) { iLoadAlert.setPercentageOfDownload(double)}
        }
    }

    override fun clientActionForSendType1(socket: Socket, files: List<File>) {
        val internetProtocolAddress: String = socket.inetAddress.toString().substring(1)
        DataTransfer.sendDataFromClient(
            InetSocketAddress(internetProtocolAddress, socket.port),
            1,
            object: IDelegateMethodSocketAction {
                override fun voidMethod(
                nameDevice: String,
                typeDevice: String,
                dataInputStream: DataInputStream,
                dataOutputStream: DataOutputStream,
                socket: Socket
                ) {
                    AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress, nameDevice, typeDevice)
                    try {
                        dataOutputStream.write(files.size)
                        val iLoadAlert: ILoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                        iLoadAlert.showAlert()
                        files.forEach { file -> DataTransferFromFile.sendDataFromFile(
                                file,
                                dataOutputStream,
                                dataInputStream,
                                setTextForLoadAlert(iLoadAlert),
                                setPercentageOfDownloadForLoadAlert(iLoadAlert)
                        )}
                        iLoadAlert.closeAlert()
                    }
                    catch (E: IOException) { E.printStackTrace() }
                }
            }
        )
    }

    override fun clientActionForSendType2(socket: Socket, files: List<File>) {
        val internetProtocolAddress: String = socket.inetAddress.toString().substring(1)
        DataTransfer.sendDataFromClient(
            InetSocketAddress(internetProtocolAddress, socket.port),
            2,
            object : IDelegateMethodSocketAction {
                override fun voidMethod(
                    nameDevice: String,
                    typeDevice: String,
                    dataInputStream: DataInputStream,
                    dataOutputStream: DataOutputStream,
                    socket: Socket
                ) {
                    AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress, nameDevice, typeDevice)
                    try {
                        dataOutputStream.write(files.size)
                        val iLoadAlert: ILoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                        iLoadAlert.showAlert()
                        files.forEach { file ->
                            run {
                                if (file.isDirectory) {
                                    try {
                                        dataOutputStream.writeBoolean(true)
                                        DataTransferFromDirectory.sendDataFromDirectory(
                                                file,
                                                file,
                                                dataOutputStream,
                                                dataInputStream,
                                                setTextForLoadAlert(iLoadAlert),
                                                setPercentageOfDownloadForLoadAlert(iLoadAlert)
                                        )
                                    } catch (E: IOException) { println("Failed to send directory: ${file.name}") }
                                } else {
                                    try {
                                        dataOutputStream.writeBoolean(false)
                                        DataTransferFromFile.sendDataFromFile(
                                                file,
                                                dataOutputStream,
                                                dataInputStream,
                                                setTextForLoadAlert(iLoadAlert),
                                                setPercentageOfDownloadForLoadAlert(iLoadAlert)
                                        )
                                    } catch (E: IOException) { println("Failed to send file: ${file.name}") }
                                }
                            }
                        }
                        iLoadAlert.closeAlert()
                    }
                    catch (E: IOException) { E.printStackTrace() }
                }
            }
        )
    }

    override fun clientActionForSendType3(socket: Socket) {
        val internetProtocolAddress: String = socket.inetAddress.toString().substring(1)
        DataTransfer.sendDataFromClient(
            InetSocketAddress(internetProtocolAddress, socket.port),
            3,
            object : IDelegateMethodSocketAction {
                override fun voidMethod(
                    nameDevice: String,
                    typeDevice: String,
                    dataInputStream: DataInputStream,
                    dataOutputStream: DataOutputStream,
                    socket: Socket
                ) {
                    AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress, nameDevice, typeDevice)
                    try {
                        AppConfig.SystemClipboard.iSystemClipboard.setContent(dataInputStream.readUTF())
                    }
                    catch (E: Exception) {
                        E.printStackTrace()
                    }
                }
            }
        )
    }

    override fun clientActionForSendType4(socket: Socket) {
        val internetProtocolAddress: String = socket.inetAddress.toString().substring(1)
        DataTransfer.sendDataFromClient(
            InetSocketAddress(internetProtocolAddress, socket.port),
            4,
            object : IDelegateMethodSocketAction {
                override fun voidMethod(
                    nameDevice: String,
                    typeDevice: String,
                    dataInputStream: DataInputStream,
                    dataOutputStream: DataOutputStream,
                    socket: Socket
                ) {
                    AppConfig.DataBase.ModelDAOInterface
                        .iDeviceModelDAO
                        .addNewDeviceToDatabaseWithUsingFilter(
                            internetProtocolAddress, nameDevice, typeDevice
                        )
                    try {
                        dataOutputStream.writeUTF(AppConfig.SystemClipboard.iSystemClipboard.getContent())
                    }
                    catch (E: Exception) {
                        E.printStackTrace()
                    }
                }
            }
        )
    }

    override fun serverActionForSendType1(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, clientNameDevice: String, clientDeviceType: String, endMethod: IDelegateMethod) {
        val fileSetSize = dataInputStream.read()
        AppConfig.AlertInterface.iMessage.showMessageLikeQuestion(
            "Do you want accept files? " +
                    "\nNumber of files: $fileSetSize " +
                    "\nDevice name: $clientNameDevice " +
                    "\nDevice type: $clientDeviceType",
            IfYesMethod(
                object : IDelegateMethod {
                    override fun voidMethod() {
                        val iLoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                        iLoadAlert.showAlert()
                        for (i in 0 until fileSetSize)
                            DataTransferFromFile.acceptDataFromFile(dataInputStream, dataOutputStream, AppOption.DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath,
                                object : IDelegateMethodStringArg {
                                    override fun voidMethod(text: String) {
                                        iLoadAlert.setContentText(text)
                                    }
                                }, object : IDelegateMethodDoubleArg {
                                    override fun voidMethod(double: Double) {
                                        iLoadAlert.setPercentageOfDownload(double)
                                    }
                                }
                            )
                        iLoadAlert.closeAlert()
                    }
                },
                endMethod
            ),
            ifNoAction = endMethod
        )
    }

    override fun serverActionForSendType2(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, clientNameDevice: String, clientDeviceType: String, endMethod: IDelegateMethod) {
        val fileSetSize = dataInputStream.read()
        AppConfig.AlertInterface.iMessage.showMessageLikeQuestion(
            "Do you want accept directories with files? " +
                    "\nNumber of data: $fileSetSize " +
                    "\nDevice name: $clientNameDevice " +
                    "\nDevice type: $clientDeviceType",
            IfYesMethod(
                object : IDelegateMethod {
                    override fun voidMethod() {
                        val iLoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                        iLoadAlert.showAlert()
                        for (i in 0 until fileSetSize) {
                            val isDirectoryData = dataInputStream.readBoolean()
                            if (isDirectoryData)
                                DataTransferFromDirectory.acceptDataFromDirectory(dataInputStream, dataOutputStream,
                                    object : IDelegateMethodStringArg {
                                        override fun voidMethod(text: String) {
                                            iLoadAlert.setContentText(text)
                                        }
                                    }, object : IDelegateMethodDoubleArg {
                                        override fun voidMethod(double: Double) {
                                            iLoadAlert.setPercentageOfDownload(double)
                                        }
                                    }
                                )
                            else
                                DataTransferFromFile.acceptDataFromFile(dataInputStream,
                                    dataOutputStream,
                                    AppOption.DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath,
                                    object : IDelegateMethodStringArg {
                                        override fun voidMethod(text: String) {
                                            iLoadAlert.setContentText(text)
                                        }
                                    },
                                    object : IDelegateMethodDoubleArg {
                                        override fun voidMethod(double: Double) {
                                            iLoadAlert.setPercentageOfDownload(double)
                                        }
                                    }
                                )
                        }
                        iLoadAlert.closeAlert()
                    }
                },
                endMethod
            ),
            ifNoAction = endMethod
        )
    }

    override fun serverActionForSendType3(dataOutputStream: DataOutputStream, endMethod: IDelegateMethod) {
        dataOutputStream.writeUTF(AppConfig.SystemClipboard.iSystemClipboard.getContent())
        endMethod.voidMethod()
    }

    override fun serverActionForSendType4(dataInputStream: DataInputStream, endMethod: IDelegateMethod) {
        AppConfig.SystemClipboard.iSystemClipboard.setContent(dataInputStream.readUTF())
        endMethod.voidMethod()
    }

    /**
     *
     */
    private class IfYesMethod(method: IDelegateMethod, endMethod: IDelegateMethod): IDelegateMethod {

        val method: IDelegateMethod = method
        val endMethod: IDelegateMethod = endMethod

        override fun voidMethod() {
            method.voidMethod()
            AppConfig.AlertInterface.iMessage.showMessage("That's all data")
            endMethod.voidMethod()
        }

    }

}