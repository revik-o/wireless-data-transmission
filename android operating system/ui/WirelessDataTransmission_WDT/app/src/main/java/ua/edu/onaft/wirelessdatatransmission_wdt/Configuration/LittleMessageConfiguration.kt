package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.widget.Toast
import androidx.core.content.ContextCompat
import com.WDTComponents.AlertInterfaces.ILittleMessage
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

class LittleMessageConfiguration: ILittleMessage {
    override fun showMessage(strMessage: String) {
        ContextCompat.getMainExecutor(SessionState.context).execute {
            Toast.makeText(SessionState.context, strMessage, Toast.LENGTH_LONG).show()
        }
    }
}