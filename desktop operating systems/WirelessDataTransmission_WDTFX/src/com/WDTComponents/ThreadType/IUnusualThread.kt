package com.WDTComponents.ThreadType

import com.WDTComponents.DelegateMethods.IDelegateMethod

interface IUnusualThread {
    fun execute(iDelegateMethod: IDelegateMethod);
}