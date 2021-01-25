package sample.lib.DeviceWork

import sample.lib.DelegateFunction.IDelegateFunction4Action
import sample.lib.SocketCommunication.DEVICE_TYPE
import sample.lib.SocketCommunication.LOCAL_DEVICE_NAME
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.ConnectException
import java.net.InetSocketAddress
import java.net.Socket

fun sendData(socketAddress: InetSocketAddress, action: IDelegateFunction4Action) {
    val socket = Socket()
    try
    {
        socket.connect(socketAddress, 500)
        val dataInputStream = DataInputStream(BufferedInputStream(socket.getInputStream()))
        val dataOutputStream = DataOutputStream(socket.getOutputStream())
        val nameDevice: String = dataInputStream.readUTF()
        val typeDevice: String = dataInputStream.readUTF()
        dataOutputStream.writeUTF(LOCAL_DEVICE_NAME)
        dataOutputStream.writeUTF(DEVICE_TYPE)
        action.voidFunction(nameDevice, typeDevice, dataInputStream, dataOutputStream, socket)
        socket.close()
    }
    catch (E: ConnectException)
    {
        println("This IP is empty")
        try { socket.close() }
        catch (E2: IOException) {}
    }
    catch (E: Exception)
    {
        println("Can't connect to ${socketAddress.address.toString().substring(1)}")
        try { socket.close() }
        catch (E2: IOException) {}
    }
}