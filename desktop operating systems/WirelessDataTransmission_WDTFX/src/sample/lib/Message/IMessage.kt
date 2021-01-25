package sample.lib.Message

import sample.lib.DelegateFunction.IDelegateFunction

interface IMessage {

    fun showMessage4AcceptData(strMessage: String, action: IDelegateFunction)
    fun showMessage(strMessage: String)

}