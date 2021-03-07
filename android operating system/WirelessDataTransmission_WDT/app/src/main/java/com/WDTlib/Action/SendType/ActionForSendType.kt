package com.WDTlib.Action.SendType

import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat
import com.WDTlib.AlertInterface.ILoadAlert
import com.WDTlib.AppConfig
import com.WDTlib.AppOption
import com.WDTlib.DelegateMethods.IDelegateMethod
import com.WDTlib.DelegateMethods.IDelegateMethodDoubleArg
import com.WDTlib.DelegateMethods.IDelegateMethodSocketAction
import com.WDTlib.DelegateMethods.IDelegateMethodStringArg
import com.WDTlib.WorkingWithData.DataTransfer
import com.WDTlib.WorkingWithData.WorkingWithFilesAndDirectories.DataTransferFromDirectory
import com.WDTlib.WorkingWithData.WorkingWithFilesAndDirectories.DataTransferFromFile
import ua.edu.onaft.wirelessdatatransmission_wdt.State.StaticState

//import java.awt.Toolkit
//import java.awt.datatransfer.DataFlavor
//import java.awt.datatransfer.StringSelection
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


    override fun clientActionForSendType1(socket: Socket, fileSet: Set<File>) {
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
                        dataOutputStream.write(fileSet.size)
                        val iLoadAlert: ILoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                        iLoadAlert.showAlert()
                        fileSet.forEach { file -> DataTransferFromFile.sendDataFromFile(file, dataOutputStream, dataInputStream, setTextForLoadAlert(iLoadAlert))}
                        iLoadAlert.closeAlert()
                    }
                    catch (E: IOException) { E.printStackTrace() }
                }
            }
        )
    }

    override fun clientActionForSendType2(socket: Socket, fileSet: Set<File>) {
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
                        dataOutputStream.write(fileSet.size)
                        val iLoadAlert: ILoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                        iLoadAlert.showAlert()
                        fileSet.forEach { file ->
                            run {
                                if (file.isDirectory) {
                                    try {
                                        dataOutputStream.writeBoolean(true)
                                        DataTransferFromDirectory.sendDataFromDirectory(file, file, dataOutputStream, dataInputStream, setTextForLoadAlert(iLoadAlert))
                                    } catch (E: IOException) { println("Failed to send directory: ${file.name}") }
                                } else {
                                    try {
                                        dataOutputStream.writeBoolean(false)
                                        DataTransferFromFile.sendDataFromFile(file, dataOutputStream, dataInputStream, setTextForLoadAlert(iLoadAlert))
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
                    try { //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(dataInputStream.readUTF()), null)
                    }
                    catch (E: Exception) {
                        E.printStackTrace()
                    }
                }
            }
        )
    }

    override fun serverActionForSendType1(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, clientNameDevice: String, clientDeviceType: String) {
        val fileSetSize = dataInputStream.read()
        AppConfig.AlertInterface.iMessage.showMessageLikeQuestion(
            "Do you want accept files? " +
                    "\nNumber of files: $fileSetSize " +
                    "\nDevice name: $clientNameDevice " +
                    "\nDevice type: $clientDeviceType",
            object : IDelegateMethod {
                override fun voidMethod() {
                    val iLoadAlert = AppConfig.AlertInterface.iLoadAlert.getConstructor().newInstance()
                    iLoadAlert.showAlert()
                    for (i in 0 until fileSetSize)
                        DataTransferFromFile.acceptDataFromFile(dataInputStream, dataOutputStream, AppOption.Option.DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath,
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
            }
        )
        AppConfig.AlertInterface.iMessage.showMessage("That's all Files")
    }

    override fun serverActionForSendType2(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, clientNameDevice: String, clientDeviceType: String) {
        val fileSetSize = dataInputStream.read()
        AppConfig.AlertInterface.iMessage.showMessageLikeQuestion(
            "Do you want accept directories with files? " +
                    "\nNumber of data: $fileSetSize " +
                    "\nDevice name: $clientNameDevice " +
                    "\nDevice type: $clientDeviceType",
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
                            DataTransferFromFile.acceptDataFromFile(dataInputStream, dataOutputStream, AppOption.Option.DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath,
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
                    }
                    iLoadAlert.closeAlert()
                    AppConfig.AlertInterface.iMessage.showMessage("Client is release")
                }
            }
        )
    }

    override fun serverActionForSendType3(dataOutputStream: DataOutputStream) { //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        dataOutputStream.writeUTF(Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as String)
    }

}