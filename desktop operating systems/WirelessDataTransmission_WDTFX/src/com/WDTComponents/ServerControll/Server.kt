package com.WDTComponents.ServerControll

import com.WDTComponents.AppOption
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.net.ConnectException
import java.net.Socket

/**
 *
 */
class Server: IServer {

    /**
     *
     */
    override fun startServerSocket() {
        AppOption.SERVER_SOCKET_IS_ON = true
        ServerSocketThread().start()
    }

    /**
     *
     */
    override fun stopServerSocket() {
        AppOption.SERVER_SOCKET_IS_ON = false
        // CHANGE
        try {
            val socket = Socket("127.0.0.1", AppOption.SOCKET_PORT)
            socket.getOutputStream().write(0)
            try {
                val dataInputStream = DataInputStream(BufferedInputStream(socket.getInputStream()))
                dataInputStream.readUTF()
                dataInputStream.readUTF()
                dataInputStream.close()
            } catch (e: Exception) { println("Can't accept data --is not necessary") }
            socket.close()
        }
        catch (E: ConnectException) {}
        // CHANGE
    }

}