package sample.lib.SocketCommunication

import sample.Platform.AlertMessage
import sample.lib.Server.startServerSocket
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.net.ConnectException
import java.net.InetAddress
import java.net.Socket

val SOCKET_PORT = 4000
    get

val MAX_COUNT_OF_CONNECT = 100
    get

var LOCAL_DEVICE_NAME = "${System.getProperty("user.name")} ${System.getProperty("os.name")} ${InetAddress.getLocalHost().hostName}"
    get set

val DEVICE_TYPE = "COMPUTER"
    get

var SERVER_SOCKET_IS_ON = true
    get
    set(value) {
        field = value
        var serverSocketIsWork = try {
            val socket = Socket("127.0.0.1", SOCKET_PORT)
            socket.getOutputStream().write(0)
            try {
                val dataInputStream = DataInputStream(BufferedInputStream(socket.getInputStream()))
                dataInputStream.readUTF()
                dataInputStream.readUTF()
                dataInputStream.close()
            } catch (e: Exception) { println("Can't accept data --is not necessary") }
            socket.close()
            true
        } catch (e: ConnectException) {
            false
        }
        if (value) {
            println(serverSocketIsWork)
            if (!serverSocketIsWork) startServerSocket(AlertMessage())
            println("Reboot server")
        }
    }