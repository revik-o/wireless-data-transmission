package com.WDTComponents.Action.SendType

import com.WDTComponents.AlertInterfaces.ILoadAlert
import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption
import com.WDTComponents.ArgClass.FileInfo
import com.WDTComponents.DataBase.Model.DeviceModel
import com.WDTComponents.DataBase.Model.TrustedDeviceModel
import com.WDTComponents.DelegateMethods.*
import com.WDTComponents.WorkingWithData.DataTransfer
import com.WDTComponents.WorkingWithData.WorkingWithFilesAndDirectories.DataTransferFromDirectory
import com.WDTComponents.WorkingWithData.WorkingWithFilesAndDirectories.DataTransferFromFile
import java.io.*
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
                CommonSendType1(
                        internetProtocolAddress,
                        files.size,
                        object : IDelegateMethodSocketActionWithLoadAlert {
                            override fun voidMethod(
                                    loadAlert: ILoadAlert,
                                    nameDevice: String,
                                    typeDevice: String,
                                    ipStaring: String,
                                    dataInputStream: DataInputStream,
                                    dataOutputStream: DataOutputStream,
                                    socket: Socket
                            ) {
                                files.forEach { file -> DataTransferFromFile.sendDataFromFile(
                                        file.name,
                                        file.length(),
                                        file.absolutePath,
                                        FileInputStream(file),
                                        dataOutputStream,
                                        dataInputStream,
                                        setTextForLoadAlert(loadAlert),
                                        setPercentageOfDownloadForLoadAlert(loadAlert)
                                )
                                }
                            }
                        }
                )
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
                            ipStaring: String,
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
                                                    file.name,
                                                    file.length(),
                                                    file.absolutePath,
                                                    FileInputStream(file),
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
                            ipStaring: String,
                            dataInputStream: DataInputStream,
                            dataOutputStream: DataOutputStream,
                            socket: Socket
                    ) {
                        AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress, nameDevice, typeDevice)
                        try {
                            val data: String = dataInputStream.readUTF()
                            AppConfig.SystemClipboard.iSystemClipboard.setContent(data)
                            AppConfig.OpenDataMethod.iOpenDataMethod.processForClipboard(data)
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
                            ipStaring: String,
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

    override fun clientActionForSendType5(socket: Socket, fileInfoList: List<FileInfo>) {
        val internetProtocolAddress: String = socket.inetAddress.toString().substring(1)
        DataTransfer.sendDataFromClient(
                InetSocketAddress(internetProtocolAddress, socket.port),
                1,
                CommonSendType1(
                        internetProtocolAddress,
                        fileInfoList.size,
                        object : IDelegateMethodSocketActionWithLoadAlert {
                            override fun voidMethod(
                                    loadAlert: ILoadAlert,
                                    nameDevice: String,
                                    typeDevice: String,
                                    ipStaring: String,
                                    dataInputStream: DataInputStream,
                                    dataOutputStream: DataOutputStream,
                                    socket: Socket
                            ) {
                                fileInfoList.forEach { file -> DataTransferFromFile.sendDataFromFile(
                                        file.fileName,
                                        file.fileSize,
                                        file.filePath,
                                        file.inputStream,
                                        dataOutputStream,
                                        dataInputStream,
                                        setTextForLoadAlert(loadAlert),
                                        setPercentageOfDownloadForLoadAlert(loadAlert)
                                )
                                }
                            }
                        }
                )
        )
    }

    override fun serverActionForSendType1(
            dataInputStream: DataInputStream,
            dataOutputStream: DataOutputStream,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            endMethod: IDelegateMethod
    ) {
        val fileSetSize = dataInputStream.read()
        commonFunc(
                IfYesMethod(
                        object : IDelegateMethod {
                            override fun voidMethod() {
                                val iLoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                                iLoadAlert.showAlert()
                                var filePath = ""
                                val isOneFile = fileSetSize == 1
                                for (i in 0 until fileSetSize)
                                    DataTransferFromFile.acceptDataFromFile(dataInputStream, dataOutputStream,
                                            clientNameDevice,
                                            clientDeviceType,
                                            clientIP,
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
                                            },
                                            object : IDelegateMethodStringArg {
                                                override fun voidMethod(text: String) {
                                                    if (isOneFile)
                                                        filePath = text
                                                }
                                            }
                                    )
                                iLoadAlert.closeAlert()
                                if (filePath != "")
                                    AppConfig.OpenDataMethod.iOpenDataMethod.openFile(filePath)
                                else if (!isOneFile)
                                    AppConfig.OpenDataMethod.iOpenDataMethod.openDownloadFolder()
                            }
                        },
                        endMethod,
                        true
                ),
                clientNameDevice,
                clientDeviceType,
                clientIP,
                fileSetSize,
                endMethod
        )
    }

    override fun serverActionForSendType2(
            dataInputStream: DataInputStream,
            dataOutputStream: DataOutputStream,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            endMethod: IDelegateMethod
    ) {
        val fileSetSize = dataInputStream.read()
        commonFunc(
                IfYesMethod(
                        object : IDelegateMethod {
                            override fun voidMethod() {
                                val iLoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                                iLoadAlert.showAlert()
                                val isOneDirectory = fileSetSize == 1
                                var directoryPath = ""
                                for (i in 0 until fileSetSize) {
                                    val isDirectoryData = dataInputStream.readBoolean()
                                    if (isDirectoryData)
                                        DataTransferFromDirectory.acceptDataFromDirectory(dataInputStream, dataOutputStream,
                                                clientNameDevice,
                                                clientDeviceType,
                                                clientIP,
                                                object : IDelegateMethodStringArg {
                                                    override fun voidMethod(text: String) {
                                                        iLoadAlert.setContentText(text)
                                                    }
                                                },
                                                object : IDelegateMethodDoubleArg {
                                                    override fun voidMethod(double: Double) {
                                                        iLoadAlert.setPercentageOfDownload(double)
                                                    }
                                                },
                                                object : IDelegateMethodStringArg {
                                                    override fun voidMethod(text: String) {
                                                        if (isOneDirectory)
                                                            directoryPath = text
                                                    }
                                                }
                                        )
                                    else
                                        DataTransferFromFile.acceptDataFromFile(dataInputStream,
                                                dataOutputStream,
                                                clientNameDevice,
                                                clientDeviceType,
                                                clientIP,
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
                                                },
                                                object : IDelegateMethodStringArg {
                                                    override fun voidMethod(text: String) {}
                                                }
                                        )
                                }
                                iLoadAlert.closeAlert()
                                if (directoryPath != "")
                                    AppConfig.OpenDataMethod.iOpenDataMethod.openFolderInFileManager(directoryPath)
                                else if (!isOneDirectory)
                                    AppConfig.OpenDataMethod.iOpenDataMethod.openDownloadFolder()
                            }
                        },
                        endMethod,
                        true
                ),
                clientNameDevice,
                clientDeviceType,
                clientIP,
                fileSetSize,
                endMethod
        )
    }

    override fun serverActionForSendType3(
            dataOutputStream: DataOutputStream,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            endMethod: IDelegateMethod
    ) {
        dataOutputStream.writeUTF(AppConfig.SystemClipboard.iSystemClipboard.getContent())
        AppConfig.AlertInterface.littleIMessage.showMessage("Sent new data to clipboard")
        endMethod.voidMethod()
    }

    override fun serverActionForSendType4(
            dataInputStream: DataInputStream,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            endMethod: IDelegateMethod
    ) {
        val buffClipboardData: String = dataInputStream.readUTF()
        AppConfig.SystemClipboard.iSystemClipboard.setContent(buffClipboardData)
        AppConfig.AlertInterface.littleIMessage.showMessage("Added new data to clipboard")
        AppConfig.OpenDataMethod.iOpenDataMethod.processForClipboard(buffClipboardData)
        endMethod.voidMethod()
    }

    private fun commonFunc(
            iDelegateMethod: IDelegateMethod,
            clientNameDevice: String,
            clientDeviceType: String,
            clientIP: String,
            fileSetSize: Int,
            endMethod: IDelegateMethod
    ) {
        val deviceId: Int = AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.getDeviceId(DeviceModel(clientNameDevice, clientDeviceType, clientIP))
        AppConfig.DataBase.ModelDAOInterface.iTrustedDeviceModelDAO.getTrustedDevice(TrustedDeviceModel(deviceId, "")).also {
            if (it.id != 0) {
                iDelegateMethod.voidMethod()
            } else {
                AppConfig.AlertInterface.iMessage.showMessageLikeQuestion(
                        "Do you want accept files? " +
                                "\nNumber of files: $fileSetSize " +
                                "\nDevice name: $clientNameDevice " +
                                "\nDevice type: $clientDeviceType",
                        ifYesAction = object : IDelegateMethod {
                            override fun voidMethod() {
                                AppConfig.AlertInterface.iMessage.showMessageLikeQuestion(
                                        "Do you want add this device like Trusted?\n" +
                                                "Name: $clientNameDevice\n" +
                                                "Type device: $clientDeviceType\n",
                                        object : IDelegateMethod {
                                            override fun voidMethod() {
                                                AppConfig.DataBase.ModelDAOInterface.iTrustedDeviceModelDAO
                                                        .addNewTrustedDevice(
                                                                TrustedDeviceModel(deviceId, "")
                                                        )
                                            }
                                        },
                                        object : IDelegateMethod {
                                            override fun voidMethod() {}
                                        }
                                )
                                Thread { iDelegateMethod.voidMethod() }.start()
                            }
                        },
                        ifNoAction = endMethod
                )
            }
        }
    }

    /**
     *
     */
    private class IfYesMethod(
            private val method: IDelegateMethod,
            private val endMethod: IDelegateMethod,
            private val showMessage: Boolean
    ): IDelegateMethod {

        override fun voidMethod() {
            method.voidMethod()
            if (showMessage) AppConfig.AlertInterface.messageForNotifyAboutCompleteDownloadProcess
                    .showMessage("That's all data")
            endMethod.voidMethod()
        }

    }

    /**
     *
     */
    private class CommonSendType1(
            internetProtocolAddress: String,
            listSize: Int,
            iDelegateMethod: IDelegateMethodSocketActionWithLoadAlert
    ): IDelegateMethodSocketAction {

        val internetProtocolAddress: String = internetProtocolAddress
        val listSize: Int = listSize
        val iDelegateMethod: IDelegateMethodSocketActionWithLoadAlert = iDelegateMethod

        override fun voidMethod(
                nameDevice: String,
                typeDevice: String,
                ipStaring: String,
                dataInputStream: DataInputStream,
                dataOutputStream: DataOutputStream,
                socket: Socket
        ) {
            AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO
                    .addNewDeviceToDatabaseWithUsingFilter(
                            internetProtocolAddress,
                            nameDevice,
                            typeDevice
                    )
            try {
                dataOutputStream.write(listSize)
                val iLoadAlert: ILoadAlert = AppConfig.AlertInterface.iLoadAlert
                        .getConstructor().newInstance()
                iLoadAlert.showAlert()
                iDelegateMethod.voidMethod(
                        iLoadAlert, nameDevice, typeDevice, ipStaring, dataInputStream, dataOutputStream, socket
                )
                iLoadAlert.closeAlert()
            }
            catch (E: IOException) { E.printStackTrace() }
        }
    }

}