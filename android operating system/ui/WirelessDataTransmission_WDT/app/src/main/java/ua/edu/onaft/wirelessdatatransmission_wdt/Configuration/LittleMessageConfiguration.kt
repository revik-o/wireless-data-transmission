package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.widget.Toast
import com.WDTComponents.AlertInterfaces.IMainMessage
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

class LittleMessageConfiguration: IMainMessage {
    override fun showMessage(strMessage: String) {
        SessionState.activity.runOnUiThread {
            Toast.makeText(SessionState.activity, strMessage, Toast.LENGTH_LONG).show()
        }
    }
}