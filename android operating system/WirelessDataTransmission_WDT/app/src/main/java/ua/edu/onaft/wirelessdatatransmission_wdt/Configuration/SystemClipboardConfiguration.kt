package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

import com.WDTComponents.SystemClipboard.ISystemClipboard

import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState

class SystemClipboardConfiguration: ISystemClipboard {

    override fun setContent(o: Any) {
        (StaticState.activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                ClipData.newPlainText("WDT data", o as String)
        )
    }

    override fun getContent(): Any? = (StaticState.activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip?.getItemAt(0)?.text

}