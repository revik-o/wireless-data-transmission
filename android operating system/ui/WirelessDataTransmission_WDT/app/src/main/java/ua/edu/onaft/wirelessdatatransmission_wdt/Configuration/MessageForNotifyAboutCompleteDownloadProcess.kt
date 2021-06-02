package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import com.WDTComponents.AlertInterfaces.ILittleMessage

class MessageForNotifyAboutCompleteDownloadProcess: ILittleMessage{

    override fun showMessage(strMessage: String) {
        LittleMessageConfiguration().showMessage(strMessage)
    }

}