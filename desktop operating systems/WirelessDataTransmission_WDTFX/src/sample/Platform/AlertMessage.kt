package sample.Platform

import sample.lib.DelegateFunction.IDelegateFunction
import sample.lib.Message.IMessage
import javax.swing.JOptionPane

class AlertMessage: IMessage {

    override fun showMessage4AcceptData(strMessage: String, action: IDelegateFunction) {
        val alert = JOptionPane.showConfirmDialog(null, strMessage, "Accept Data", JOptionPane.YES_NO_OPTION)
        if (alert == JOptionPane.YES_OPTION)
            action.voidFunction()
    }

}