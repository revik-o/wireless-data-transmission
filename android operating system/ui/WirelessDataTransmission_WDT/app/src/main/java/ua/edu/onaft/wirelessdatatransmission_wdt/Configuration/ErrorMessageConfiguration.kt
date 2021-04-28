package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.app.AlertDialog
import com.WDTComponents.AlertInterfaces.IMainMessage
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

class ErrorMessageConfiguration: IMainMessage {
    override fun showMessage(strMessage: String) {
        SessionState.activity.runOnUiThread {
            AlertDialog.Builder(SessionState.activity).setTitle("Error").setMessage(strMessage).setNeutralButton("Ok") { dialog, which -> }.show()
        }
    }
}