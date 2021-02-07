package sample.WDTComponents.WorkingWithData

import sample.WDTComponents.AppConfig
import sample.WDTComponents.AppOption
import sample.WDTComponents.DelegateMethods.IDelegateMethodSocketAction
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.ConnectException
import java.net.InetSocketAddress
import java.net.Socket

object DataTransfer {

    fun sendDataFromClient(socketAddress: InetSocketAddress, sendType: Int, action: IDelegateMethodSocketAction, localDeviceIPAndRepeatableIPsList: ArrayList<String>) {
        this.sendDataFromClient(socketAddress, sendType, object : IDelegateMethodSocketAction {

            override fun voidMethod(nameDevice: String, typeDevice: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, socket: Socket) {
                localDeviceIPAndRepeatableIPsList.add(socketAddress.address.toString().substring(1))
                action.voidMethod(nameDevice, typeDevice, dataInputStream, dataOutputStream, socket)
            }

        })
    }

    fun sendDataFromClient(socketAddress: InetSocketAddress, sendType: Int, action: IDelegateMethodSocketAction) {
        val socket = Socket()
        try
        {
            socket.connect(socketAddress, AppOption.SOCKET_TIMEOUT)
            val dataInputStream = DataInputStream(BufferedInputStream(socket.getInputStream()))
            val dataOutputStream = DataOutputStream(socket.getOutputStream())
            val nameDevice: String = dataInputStream.readUTF()
            val typeDevice: String = dataInputStream.readUTF()
            dataOutputStream.writeUTF(AppOption.LOCAL_DEVICE_NAME)
            dataOutputStream.writeUTF(AppOption.DEVICE_TYPE)
            dataOutputStream.write(sendType)
            action.voidMethod(nameDevice, typeDevice, dataInputStream, dataOutputStream, socket)
            socket.close()
        }
        catch (E: ConnectException)
        {
            println("This IP is empty")
            try { socket.close() } catch (E2: IOException) {}
        }
        catch (E: Exception)
        {
            println("Can't connect to ${socketAddress.address.toString().substring(1)}")
            E.printStackTrace()
            try { socket.close() } catch (E2: IOException) {}
        }
    }

    fun acceptDataOnServer(socket: Socket) {
        val dataInputStream = DataInputStream(BufferedInputStream(socket.getInputStream()))
        val dataOutputStream = DataOutputStream(socket.getOutputStream())
        dataOutputStream.writeUTF(AppOption.LOCAL_DEVICE_NAME)
        dataOutputStream.writeUTF(AppOption.DEVICE_TYPE)
        val clientNameDevice: String = dataInputStream.readUTF()
        val clientDeviceType: String = dataInputStream.readUTF()
        AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.addNewDeviceToDatabaseWithUsingFilter(socket.inetAddress.toString().substring(1), clientNameDevice, clientDeviceType)
        val sendType = dataInputStream.read()
        /**
         * sendType == 1 - accept files
         * sendType == 2 - accept directories & files
         * sendType == 3 - send Clipboard
         */
        if (!AppOption.DIRECTORY_FOR_DOWNLOAD_FILES.exists()) AppOption.DIRECTORY_FOR_DOWNLOAD_FILES.mkdirs()
        when (sendType) {
            1 -> AppConfig.Action.SendTypeInterface.iActionForSendType.serverActionForSendType1(dataInputStream, dataOutputStream, clientNameDevice, clientDeviceType)
            2 -> AppConfig.Action.SendTypeInterface.iActionForSendType.serverActionForSendType2(dataInputStream, dataOutputStream, clientNameDevice, clientDeviceType)
            3 -> AppConfig.Action.SendTypeInterface.iActionForSendType.serverActionForSendType3(dataOutputStream)
//            else -> {}
        }
        dataInputStream.close()
        dataOutputStream.close()
        socket.close()
        println("Client is release")
    }

}