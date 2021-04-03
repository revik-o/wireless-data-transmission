package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.app.AlertDialog

import com.WDTComponents.AlertInterfaces.IMainMessage

import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState

class ErrorMessageConfiguration: IMainMessage {
    override fun showMessage(strMessage: String) {
        StaticState.activity.runOnUiThread {
            AlertDialog.Builder(StaticState.activity).setTitle("Error").setMessage(strMessage).setNeutralButton("Ok") { dialog, which -> }.show()
        }
    }
}