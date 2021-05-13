package com.WDTComponents.ThreadType

import com.WDTComponents.DelegateMethods.IDelegateMethod

@Deprecated("")
interface IUsualThread {
    fun execute(iDelegateMethod: IDelegateMethod);
}