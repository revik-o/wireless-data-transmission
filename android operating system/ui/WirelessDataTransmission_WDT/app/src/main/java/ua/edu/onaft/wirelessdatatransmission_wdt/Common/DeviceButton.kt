package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.widget.Button
import androidx.annotation.RequiresApi
import com.WDTComponents.TypeDeviceENUM
import ua.edu.onaft.wirelessdatatransmission_wdt.R

//@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@SuppressLint("UseCompatLoadingForDrawables")
class DeviceButton(activity: Activity, typeDevice: String, deviceName: String) {

    private val activity: Activity = activity
    private val typeDevice: String = typeDevice
    private val deviceName: String = deviceName

    val button: Button = LayoutInflater.from(activity).inflate(R.layout.button_device, null) as Button

    init {
        button.text = deviceName
        if (typeDevice != TypeDeviceENUM.PHONE)
            button.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                            R.drawable.ic_baseline_computer_24
                    ,
                    0,
                    0
            )
    }

}