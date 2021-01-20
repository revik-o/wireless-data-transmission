package sample.lib.Server

import sample.lib.Message.IMessage
import sample.lib.DelegateFunction.IDelegateFunction
import sample.lib.FileWork.DIRECTORY_FOR_DOWNLOAD_FILES
import sample.lib.FileWork.acceptDataFromClientDirectory
import sample.lib.FileWork.acceptDataFromClientFile
import sample.lib.SocketCommunication.DEVICE_TYPE
import sample.lib.SocketCommunication.LOCAL_DEVICE_NAME
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.concurrent.Semaphore

class WorkWithClient(socket: Socket, semaphore: Semaphore, message: IMessage): Thread() {

    private val socket: Socket = socket
    private val semaphore: Semaphore = semaphore
    private val message: IMessage = message

    override fun run() {
        val dataInputStream = DataInputStream(BufferedInputStream(this.socket.getInputStream()))
        val dataOutputStream = DataOutputStream(this.socket.getOutputStream())
        dataOutputStream.writeUTF(LOCAL_DEVICE_NAME)
        dataOutputStream.writeUTF(DEVICE_TYPE)
        val clientNameDevice: String = dataInputStream.readUTF()
        val clientDeviceType: String = dataInputStream.readUTF()
        val sendType = dataInputStream.read()
        /**
         * sendType == 1 - accept files
         * sendType == 2 - accept directories & files
         */
        if (!DIRECTORY_FOR_DOWNLOAD_FILES.exists()) DIRECTORY_FOR_DOWNLOAD_FILES.mkdirs()
        if (sendType == 1) {
            val fileSetSize = dataInputStream.read()
            message.showMessage4AcceptData("Do you want accept files? \nNumber of files: $fileSetSize \nDevice name: $clientNameDevice \nDevice type: $clientDeviceType", object : IDelegateFunction {
                override fun voidFunction() {
                    for (i in 0 until fileSetSize)
                        acceptDataFromClientFile(DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath, dataInputStream, dataOutputStream)
                }
            })
        } else if (sendType == 2) {
            val fileSetSize = dataInputStream.read()
            message.showMessage4AcceptData("Do you want accept directories with files? \nNumber of data: $fileSetSize \nDevice name: $clientNameDevice \nDevice type: $clientDeviceType", object : IDelegateFunction {
                override fun voidFunction() {
                    for (i in 0 until fileSetSize) {
                        val isDirectoryData = dataInputStream.readBoolean()
                        if (isDirectoryData)
                            acceptDataFromClientDirectory(dataInputStream, dataOutputStream)
                        else
                            acceptDataFromClientFile(DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath, dataInputStream, dataOutputStream)
                    }
                }
            })
        }
        dataInputStream.close()
        dataOutputStream.close()
        socket.close()
        println("Client is release")
        semaphore.release()
    }

}