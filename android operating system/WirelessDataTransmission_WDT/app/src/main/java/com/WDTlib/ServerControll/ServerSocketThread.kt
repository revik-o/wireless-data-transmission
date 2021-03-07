package com.WDTlib.ServerControll

import com.WDTlib.AppConfig
import com.WDTlib.AppOption

import java.net.ServerSocket
import java.util.concurrent.Semaphore

class ServerSocketThread: Thread() {

    object staticMethod {
        fun action() {
            val serverSocket = ServerSocket(AppOption.Option.SOCKET_PORT)
            println("Server start")
            val semaphore = Semaphore(AppOption.Option.MAX_COUNT_OF_CONNECT)
            while (AppOption.Option.SERVER_SOCKET_IS_ON) {
                semaphore.acquire()
                println("Start waiting new client...")
                AppConfig.WorkingWithClientInterface.iWorkingWithClient.start(serverSocket.accept(), semaphore)
            }
            serverSocket.close()
            println("Server is off")
        }
    }

    override fun run() {
        staticMethod.action()
    }

}