package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Toast

import com.WDTComponents.AlertInterfaces.IMessage
import com.WDTComponents.DelegateMethods.IDelegateMethod

import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState

class MessageConfiguration: IMessage {

    private class AlertDialogOnClickListener(ifYesAction: IDelegateMethod, ifNoAction: IDelegateMethod): DialogInterface.OnClickListener {

        private val ifYesAction: IDelegateMethod = ifYesAction
        private val ifNoAction: IDelegateMethod = ifNoAction

        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    Thread {
                        ifYesAction.voidMethod()
                    }.start()
                }
                DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(
                        StaticState.activity.applicationContext, "Canceled operation", Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun showMessage(strMessage: String) {
        StaticState.activity.runOnUiThread {
            AlertDialog.Builder(StaticState.activity).setTitle("Message").setMessage(strMessage).setNeutralButton("Ok") { dialog, which -> }.show()
        }
    }

    override fun showMessageLikeQuestion(strMessage: String, ifYesAction: IDelegateMethod, ifNoAction: IDelegateMethod) {
        val alertDialogOnClickListener = AlertDialogOnClickListener(ifYesAction, ifNoAction)
        StaticState.activity.runOnUiThread {
            AlertDialog.Builder(StaticState.activity).setMessage(strMessage)
                    .setPositiveButton("Yes", alertDialogOnClickListener)
                    .setNegativeButton("No", alertDialogOnClickListener).show()

        }
    }

}