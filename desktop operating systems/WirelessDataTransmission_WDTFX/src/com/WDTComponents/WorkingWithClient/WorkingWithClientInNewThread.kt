package com.WDTComponents.WorkingWithClient

import com.WDTComponents.DelegateMethods.IDelegateMethod

class WorkingWithClientInNewThread(iDelegateMethod: IDelegateMethod): Thread() {

    val iDelegateMethod: IDelegateMethod = iDelegateMethod

    override fun run() {
        iDelegateMethod.voidMethod()
    }

}