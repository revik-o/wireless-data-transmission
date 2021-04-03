package com.WDTComponents.AlertInterfaces

import com.WDTComponents.DelegateMethods.IDelegateMethod

interface IMessage: IMainMessage {
    fun showMessageLikeQuestion(strMessage: String, ifYesAction: IDelegateMethod, ifNoAction: IDelegateMethod)
}