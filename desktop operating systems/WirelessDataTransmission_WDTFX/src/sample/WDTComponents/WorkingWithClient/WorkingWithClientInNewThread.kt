package sample.WDTComponents.WorkingWithClient

import sample.WDTComponents.DelegateMethods.IDelegateMethod
import java.util.concurrent.Semaphore

class WorkingWithClientInNewThread(iDelegateMethod: IDelegateMethod, semaphore: Semaphore): Thread() {

    val iDelegateMethod: IDelegateMethod = iDelegateMethod
    val semaphore: Semaphore = semaphore

    override fun run() {
        iDelegateMethod.voidMethod()
        semaphore.release()
    }

}