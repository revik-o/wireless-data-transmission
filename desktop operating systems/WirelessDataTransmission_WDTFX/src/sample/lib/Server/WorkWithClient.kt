package sample.lib.Server

import sample.lib.DATABASE_ACTION
import sample.lib.DelegateFunction.IDelegateFunction
import sample.lib.FileWork.DIRECTORY_FOR_DOWNLOAD_FILES
import sample.lib.FileWork.acceptDataFromClientDirectory
import sample.lib.FileWork.acceptDataFromClientFile
import sample.lib.Message.ILoadStageMessage
import sample.lib.SocketCommunication.DEVICE_TYPE
import sample.lib.SocketCommunication.LOCAL_DEVICE_NAME
import sample.lib.publicILoadStageMessage
import sample.lib.publicIMessage4ServerSocket
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.concurrent.Semaphore

class WorkWithClient(socket: Socket, semaphore: Semaphore): Thread() {

    private val socket: Socket = socket
    private val semaphore: Semaphore = semaphore

    override fun run() {
        val dataInputStream = DataInputStream(BufferedInputStream(this.socket.getInputStream()))
        val dataOutputStream = DataOutputStream(this.socket.getOutputStream())
        dataOutputStream.writeUTF(LOCAL_DEVICE_NAME)
        dataOutputStream.writeUTF(DEVICE_TYPE)
        val clientNameDevice: String = dataInputStream.readUTF()
        val clientDeviceType: String = dataInputStream.readUTF()
        DATABASE_ACTION.addNewDeviceToDatabaseWithUsingFilter(socket.inetAddress.toString().substring(1), clientNameDevice, clientDeviceType)
        val sendType = dataInputStream.read()
        /**
         * sendType == 1 - accept files
         * sendType == 2 - accept directories & files
         */
        if (!DIRECTORY_FOR_DOWNLOAD_FILES.exists()) DIRECTORY_FOR_DOWNLOAD_FILES.mkdirs()
        if (sendType == 1) {
            val fileSetSize = dataInputStream.read()
            publicIMessage4ServerSocket.showMessage4AcceptData("Do you want accept files? \nNumber of files: $fileSetSize \nDevice name: $clientNameDevice \nDevice type: $clientDeviceType", object : IDelegateFunction {
                override fun voidFunction() {
                    val iLoadStageMessage: ILoadStageMessage =  publicILoadStageMessage.getConstructor().newInstance()
                    iLoadStageMessage.showMessage()
                    for (i in 0 until fileSetSize)
                        acceptDataFromClientFile(DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath, dataInputStream, dataOutputStream, iLoadStageMessage)
                    iLoadStageMessage.closeMessage()
                }
            })
            publicIMessage4ServerSocket.showMessage("That's all Files")
        } else if (sendType == 2) {
            val fileSetSize = dataInputStream.read()
            publicIMessage4ServerSocket.showMessage4AcceptData("Do you want accept directories with files? \nNumber of data: $fileSetSize \nDevice name: $clientNameDevice \nDevice type: $clientDeviceType", object : IDelegateFunction {
                override fun voidFunction() {
                    val iLoadStageMessage: ILoadStageMessage =  publicILoadStageMessage.getConstructor().newInstance()
                    iLoadStageMessage.showMessage()
                    for (i in 0 until fileSetSize) {
                        val isDirectoryData = dataInputStream.readBoolean()
                        if (isDirectoryData)
                            acceptDataFromClientDirectory(dataInputStream, dataOutputStream, iLoadStageMessage)
                        else
                            acceptDataFromClientFile(DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath, dataInputStream, dataOutputStream, iLoadStageMessage)
                    }
                    iLoadStageMessage.closeMessage()
                }
            })
            publicIMessage4ServerSocket.showMessage("That's all Dirs and Files")
        } else if (sendType == 3) {
            println(Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as String)
            dataOutputStream.writeUTF(Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as String)
        }
        dataInputStream.close()
        dataOutputStream.close()
        socket.close()
        println("Client is release")
        semaphore.release()
    }

}