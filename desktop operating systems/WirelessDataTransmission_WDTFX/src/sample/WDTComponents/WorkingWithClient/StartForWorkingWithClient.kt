package sample.WDTComponents.WorkingWithClient

import sample.WDTComponents.DelegateMethods.IDelegateMethod
import sample.WDTComponents.WorkingWithData.DataTransfer
import java.net.Socket
import java.util.concurrent.Semaphore

class StartForWorkingWithClient: IWorkingWithClient {
    override fun start(socket: Socket, semaphore: Semaphore) {
        WorkingWithClientInNewThread(object : IDelegateMethod { override fun voidMethod() { DataTransfer.acceptDataOnServer(socket) } }, semaphore).start()
    }
}