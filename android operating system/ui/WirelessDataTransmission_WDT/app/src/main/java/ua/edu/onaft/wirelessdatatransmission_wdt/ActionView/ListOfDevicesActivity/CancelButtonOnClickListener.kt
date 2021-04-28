package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.ListOfDevicesActivity

import android.app.Activity
import android.view.View
import com.WDTComponents.WorkingWithDevices.ScanDevicesIPVersion4

class CancelButtonOnClickListener(activity: Activity, scanDeviceService: ScanDevicesIPVersion4): View.OnClickListener {

    private val activity: Activity = activity
    private val scanDeviceService: ScanDevicesIPVersion4 = scanDeviceService

    override fun onClick(v: View?) {
        scanDeviceService.stopScanDevices()
    }

}