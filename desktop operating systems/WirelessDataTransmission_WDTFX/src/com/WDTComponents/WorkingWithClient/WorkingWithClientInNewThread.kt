package com.WDTComponents.WorkingWithClient

import com.WDTComponents.DelegateMethods.IDelegateMethod

@Deprecated("")
class WorkingWithClientInNewThread(iDelegateMethod: IDelegateMethod): Thread() {

    val iDelegateMethod: IDelegateMethod = iDelegateMethod

    override fun run() {
        iDelegateMethod.voidMethod()
    }

}