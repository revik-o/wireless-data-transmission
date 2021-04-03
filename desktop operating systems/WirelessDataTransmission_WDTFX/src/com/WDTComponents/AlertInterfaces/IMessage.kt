package com.WDTComponents.AlertInterfaces

import com.WDTComponents.DelegateMethods.IDelegateMethod

interface IMessage {

    fun showMessage(strMessage: String)
    fun showMessageLikeQuestion(strMessage: String, ifYesAction: IDelegateMethod, ifNoAction: IDelegateMethod)

}