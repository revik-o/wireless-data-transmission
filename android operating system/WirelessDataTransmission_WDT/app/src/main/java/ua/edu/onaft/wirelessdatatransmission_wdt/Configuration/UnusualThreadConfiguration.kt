package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import com.WDTComponents.DelegateMethods.IDelegateMethod
import com.WDTComponents.ThreadType.IUnusualThread
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState

@Deprecated("")
class UnusualThreadConfiguration: IUnusualThread {
    override fun execute(iDelegateMethod: IDelegateMethod) {
        StaticState.activity.runOnUiThread { iDelegateMethod.voidMethod() }
    }
}