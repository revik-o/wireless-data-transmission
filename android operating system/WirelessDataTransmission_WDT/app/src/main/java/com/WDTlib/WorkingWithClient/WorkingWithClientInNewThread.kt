package com.WDTlib.WorkingWithClient

import com.WDTlib.DelegateMethods.IDelegateMethod
import ua.edu.onaft.wirelessdatatransmission_wdt.State.StaticState

import java.util.concurrent.Semaphore

class WorkingWithClientInNewThread(iDelegateMethod: IDelegateMethod, semaphore: Semaphore): Thread() {

    val iDelegateMethod: IDelegateMethod = iDelegateMethod
    val semaphore: Semaphore = semaphore

    override fun run() {
        iDelegateMethod.voidMethod()
        StaticState.activity.runOnUiThread {
            semaphore.release()
            print("kkkkkkkkkkk!!!!!!!!!!!!!!!!!!")
        }
    }

}