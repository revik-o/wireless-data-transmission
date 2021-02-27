package ua.edu.onaft.wirelessdatatransmission_wdt

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View

object ButtonActions {

    object WelcomeActivity {

        class StartActionListener: View.OnClickListener {

            class AlertButtonAction: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when(which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                        }
                    }
                }
            }

            override fun onClick(view: View?) {
                val alertButtonAction = AlertButtonAction()
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(view?.context)
                alertDialog.setMessage("Norm?").setPositiveButton("Yes", alertButtonAction)
                    .setNegativeButton("No", alertButtonAction).setTitle("nice").show()
            }
        }

    }

}