package com.revik_o.infrastructure.tcp

import com.revik_o.config.ApplicationConfigI
import com.revik_o.core.CommunicationProtocol
import com.revik_o.core.util.AsyncUtils.runAsync
import java.net.ServerSocket
import java.net.Socket
import java.util.UUID
import java.util.concurrent.Semaphore

object TCP {

    private const val MAX_DEVICES = 255;

    private fun serverCanOperate(appConfigPtr: ApplicationConfigI): Boolean =
        appConfigPtr.isCommunicationEnabled
                && appConfigPtr.currentCommunicationProtocol == CommunicationProtocol.TCP

    /**
     * We use only one `while async` loop to work with devices, we don't need
     * to create a real server. It's just a phone!
     */
    fun start(appConfigPtr: ApplicationConfigI, tcpCommunicationService: TCPCommunicationService) {
        if (serverCanOperate(appConfigPtr)) {
            appConfigPtr.enableCommunication()
            val semaphore = Semaphore(MAX_DEVICES)

            runAsync {
                ServerSocket(appConfigPtr.tcpPort).use { server ->
                    while (serverCanOperate(appConfigPtr)) {
                        val handler = TCPCommunicationHandler(server.accept())
                        semaphore.acquire()
                        runAsync {
                            tcpCommunicationService.accept(handler)
                            semaphore.release()
                        }
                    }
                }
            }
        }
    }

    fun stop(appConfigPtr: ApplicationConfigI) {
        if (appConfigPtr.currentCommunicationProtocol == CommunicationProtocol.TCP) {
            appConfigPtr.disableCommunication()
            Socket(
                "127.0.0.1",
                appConfigPtr.tcpPort
            ).use { socket ->
                TCPCommunicationHandler(socket).send(UUID.randomUUID().toString())
            }
        }
    }
}