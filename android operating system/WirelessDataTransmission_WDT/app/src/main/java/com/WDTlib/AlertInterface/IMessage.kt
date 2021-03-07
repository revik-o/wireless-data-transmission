package com.WDTlib.AlertInterface

import com.WDTlib.DelegateMethods.IDelegateMethod

interface IMessage {

    fun showMessage(strMessage: String)
    fun showMessageLikeQuestion(strMessage: String, ifYesAction: IDelegateMethod)

}