package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.app.AlertDialog
import androidx.core.content.ContextCompat
import com.WDTComponents.AlertInterfaces.ILittleMessage
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

class ErrorMessageConfiguration: ILittleMessage {
    override fun showMessage(strMessage: String) {
//        SessionState.context.runOnUiThread {
        ContextCompat.getMainExecutor(SessionState.context).execute {
            AlertDialog.Builder(SessionState.context).setTitle("Error").setMessage(strMessage)
                .setNeutralButton("Ok") { dialog, which -> }.show()
        }
    }
}