package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Toast

import com.WDTlib.AlertInterface.IMessage
import com.WDTlib.DelegateMethods.IDelegateMethod

import ua.edu.onaft.wirelessdatatransmission_wdt.State.StaticState

class MessageConfiguration: IMessage {

    private class AlertDialogOnClickListener(action: IDelegateMethod): DialogInterface.OnClickListener {

        private val ifYesAction: IDelegateMethod = action

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
            AlertDialog.Builder(StaticState.activity).setMessage(strMessage).show()
        }
    }

    override fun showMessageLikeQuestion(strMessage: String, ifYesAction: IDelegateMethod) {
        val alertDialogOnClickListener = AlertDialogOnClickListener(ifYesAction)
        StaticState.activity.runOnUiThread {
            AlertDialog.Builder(StaticState.activity).setMessage(strMessage)
                    .setPositiveButton("Yes", alertDialogOnClickListener)
                    .setNegativeButton("No", alertDialogOnClickListener).show()

        }
    }

}