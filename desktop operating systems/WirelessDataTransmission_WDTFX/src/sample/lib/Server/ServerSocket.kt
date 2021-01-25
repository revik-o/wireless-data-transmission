package sample.lib.Server

import sample.lib.SocketCommunication.MAX_COUNT_OF_CONNECT
import sample.lib.SocketCommunication.SERVER_SOCKET_IS_ON
import sample.lib.SocketCommunication.SOCKET_PORT
import java.net.ServerSocket
import java.util.concurrent.Semaphore

fun startServerSocket() {
    Thread {
        val serverSocket = ServerSocket(SOCKET_PORT)
        println("Server start")
        val semaphore = Semaphore(MAX_COUNT_OF_CONNECT)
        while (SERVER_SOCKET_IS_ON) {
            semaphore.acquire()
            println("Start waiting new client...")
            WorkWithClient(serverSocket.accept(), semaphore).start()
        }
        serverSocket.close()
        println("Server off")
    }.start()
}