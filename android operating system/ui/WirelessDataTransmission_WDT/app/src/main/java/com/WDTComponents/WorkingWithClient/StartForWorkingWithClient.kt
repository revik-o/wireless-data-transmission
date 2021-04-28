package com.WDTComponents.WorkingWithClient

import com.WDTComponents.WorkingWithData.DataTransfer
import java.net.Socket
import java.util.concurrent.Semaphore

class StartForWorkingWithClient: IWorkingWithClient {
    override fun start(socket: Socket, semaphore: Semaphore) {
        Thread { DataTransfer.acceptDataOnServer(socket, semaphore) }.start()
    }
}