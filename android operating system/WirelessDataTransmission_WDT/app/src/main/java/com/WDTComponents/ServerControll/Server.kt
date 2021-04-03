package com.WDTComponents.ServerControll

import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption
import com.WDTComponents.DelegateMethods.IDelegateMethod

import java.io.BufferedInputStream
import java.io.DataInputStream
import java.net.ConnectException
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Semaphore

/**
 *
 */
class Server: IServer {

    /**
     *
     */
    override fun startServerSocket() {
        AppOption.SERVER_SOCKET_IS_ON = true
        AppConfig.ThreadType.iUsualThread.execute(object : IDelegateMethod {
            override fun voidMethod() {
                val serverSocket = ServerSocket(AppOption.SOCKET_PORT)
                println("Server start")
                val semaphore = Semaphore(AppOption.MAX_COUNT_OF_CONNECT)
                while (AppOption.SERVER_SOCKET_IS_ON) {
                    semaphore.acquire()
                    println("Start waiting new client...")
                    AppConfig.WorkingWithClientInterface.iWorkingWithClient.start(serverSocket.accept(), semaphore)
                }
                serverSocket.close()
                println("Server is off")
            }
        })
    }

    /**
     *
     */
    override fun stopServerSocket() {
        AppOption.SERVER_SOCKET_IS_ON = false
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
        catch (e: ConnectException) {}
    }

}