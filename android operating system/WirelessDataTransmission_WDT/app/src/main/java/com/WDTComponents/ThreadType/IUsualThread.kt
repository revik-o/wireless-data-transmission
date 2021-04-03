package com.WDTComponents.ThreadType

import com.WDTComponents.DelegateMethods.IDelegateMethod

interface IUsualThread {
    fun execute(iDelegateMethod: IDelegateMethod);
}