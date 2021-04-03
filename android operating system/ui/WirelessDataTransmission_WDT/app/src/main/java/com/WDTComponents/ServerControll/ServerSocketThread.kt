package com.WDTComponents.ServerControll

import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption

import java.net.ServerSocket
import java.util.concurrent.Semaphore

@Deprecated("")
class ServerSocketThread: Thread() {

    @Deprecated("")
    object staticMethod {
        fun action() {
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
    }

    override fun run() {
        staticMethod.action()
    }

}