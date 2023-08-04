package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.ListOfDevicesActivity

import android.view.View
import com.WDTComponents.WorkingWithDevices.ScanDevicesIPVersion4

class CancelButtonOnClickListener(scanDeviceService: ScanDevicesIPVersion4): View.OnClickListener {

    private val scanDeviceService: ScanDevicesIPVersion4 = scanDeviceService

    override fun onClick(v: View?) {
        scanDeviceService.stopScanDevices()
    }

}