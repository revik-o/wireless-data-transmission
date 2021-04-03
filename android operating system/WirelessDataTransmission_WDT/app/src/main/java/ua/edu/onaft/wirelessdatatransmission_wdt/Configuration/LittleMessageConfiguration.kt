package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.widget.Toast
import com.WDTComponents.AlertInterfaces.IMainMessage
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState

class LittleMessageConfiguration: IMainMessage {
    override fun showMessage(strMessage: String) {
        StaticState.activity.runOnUiThread {
            Toast.makeText(StaticState.activity, strMessage, Toast.LENGTH_LONG).show()
        }
    }
}