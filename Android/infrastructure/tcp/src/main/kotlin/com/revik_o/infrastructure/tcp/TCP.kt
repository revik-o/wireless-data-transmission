package com.revik_o.infrastructure.tcp

import com.revik_o.core.common.CommunicationProtocol
import com.revik_o.core.common.contexts.ApplicationSettingsContextI
import com.revik_o.core.common.utils.ConcurrencyUtils.runConcurrentOperation
import com.revik_o.infrastructure.common.OSAPIInterface
import java.net.ServerSocket
import java.util.UUID
import java.util.concurrent.Semaphore

object TCP {

    private const val MAX_DEVICES = 255;

    private fun serverCanOperate(appSettings: ApplicationSettingsContextI): Boolean =
        appSettings.isCommunicationEnabled
                && appSettings.currentCommunicationProtocol == CommunicationProtocol.TCP

    /**
     * We use only one `concurrent while loop` to work with devices, we don't need
     * to create a real server. It's just a phone!
     */
    fun start(api: OSAPIInterface) {
        val semaphore = Semaphore(MAX_DEVICES)
        val handler = TCPServerSocketHandler(api)
        api.appSettings.enableCommunication()

        runConcurrentOperation {
            ServerSocket(api.appSettings.tcpPort).use { server ->
                while (serverCanOperate(api.appSettings)) {
                    val socket = server.accept()
                    runConcurrentOperation {
                        handler.accept(socket)
                        semaphore.release()
                        socket.close()
                    }
                }

                api.appSettings.disableCommunication()
            }
        }
    }

    fun stop(api: OSAPIInterface) {
        if (api.appSettings.currentCommunicationProtocol == CommunicationProtocol.TCP) {
            api.appSettings.disableCommunication()
            val socket = configureTCPSocket(
                "127.0.0.1",
                api.appSettings.tcpPort,
                api.appSettings.awaitTimeout
            )
            TCPDataHandler(socket, api).send(UUID.randomUUID().toString())
            socket.close()
        }
    }
}