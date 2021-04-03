package com.WDTComponents.WorkingWithClient

import com.WDTComponents.AppConfig
import com.WDTComponents.DelegateMethods.IDelegateMethod
import com.WDTComponents.WorkingWithData.DataTransfer

import java.net.Socket
import java.util.concurrent.Semaphore

class StartForWorkingWithClient: IWorkingWithClient {
    override fun start(socket: Socket, semaphore: Semaphore) {
        AppConfig.ThreadType.iUsualThread.execute(object : IDelegateMethod {
            override fun voidMethod() { DataTransfer.acceptDataOnServer(socket, semaphore) }
        })
//        WorkingWithClientInNewThread(object : IDelegateMethod { override fun voidMethod() { DataTransfer.acceptDataOnServer(socket, semaphore) } }).start()
    }
}