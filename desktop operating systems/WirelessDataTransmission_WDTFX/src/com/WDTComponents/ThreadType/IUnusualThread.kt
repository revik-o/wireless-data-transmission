package com.WDTComponents.ThreadType

import com.WDTComponents.DelegateMethods.IDelegateMethod

@Deprecated("")
interface IUnusualThread {
    fun execute(iDelegateMethod: IDelegateMethod);
}