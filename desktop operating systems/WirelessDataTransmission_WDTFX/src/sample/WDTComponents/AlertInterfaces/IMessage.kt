package sample.WDTComponents.AlertInterfaces

import sample.WDTComponents.DelegateMethods.IDelegateMethod

interface IMessage {

    fun showMessage(strMessage: String)
    fun showMessageLikeQuestion(strMessage: String, ifYesAction: IDelegateMethod)

}