package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import com.WDTComponents.DelegateMethods.IDelegateMethod
import com.WDTComponents.ThreadType.IUsualThread

class UsualThreadConfiguration: IUsualThread {

    override fun execute(iDelegateMethod: IDelegateMethod) {
        Thread { iDelegateMethod.voidMethod() }.start()
    }
}