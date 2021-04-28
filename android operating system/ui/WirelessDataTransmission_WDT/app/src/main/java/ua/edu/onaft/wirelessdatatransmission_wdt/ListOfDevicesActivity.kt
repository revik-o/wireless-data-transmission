package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateMargins
import com.WDTComponents.DelegateMethods.IDelegateMethodIntegerArg
import com.WDTComponents.DelegateMethods.IDelegateMethodSocketAction
import com.WDTComponents.WorkingWithDevices.ScanDevicesIPVersion4
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.ListOfDevicesActivity.DeviceButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Action.BackArrowButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.DeviceButton
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.ScreenDimension
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ListOfDevicesActivity : AppCompatActivity() {

    private lateinit var frameLayoutAppBar: FrameLayout
    private lateinit var backArrowButton: View
    private lateinit var mainLinearLayout: ViewGroup
    private lateinit var space: View
    private lateinit var cancelButton: View
    private lateinit var scanDeviceService: ScanDevicesIPVersion4

    override fun onStart() {
        val screenDimension = ScreenDimension(this)
        val height = screenDimension.height
        val width = screenDimension.width

        frameLayoutAppBar.layoutParams.height = (height / 14.55).toInt()
        backArrowButton.layoutParams.height = (frameLayoutAppBar.layoutParams.height / 1.375).toInt()
        backArrowButton.layoutParams.width = backArrowButton.layoutParams.height
        (backArrowButton.layoutParams as FrameLayout.LayoutParams).updateMargins(left = width / 45, bottom = frameLayoutAppBar.layoutParams.height / 11)
        space.layoutParams.height = Constant.usualSpace

        backArrowButton.setOnClickListener(BackArrowButtonOnClickListener(this))

        scanDeviceService = ScanDevicesIPVersion4(
                object : IDelegateMethodSocketAction {
                    override fun voidMethod(nameDevice: String, typeDevice: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, socket: Socket) {
                        val deviceButton: Button = DeviceButton(this@ListOfDevicesActivity, typeDevice, nameDevice).button
                        deviceButton.setOnClickListener(DeviceButtonOnClickListener(socket))
                        runOnUiThread {
                            mainLinearLayout.addView(deviceButton)
                        }
                    }
                },
                object : IDelegateMethodIntegerArg {
                    override fun voidMethod(number: Int) {}
                }
        )

        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_devices)
        frameLayoutAppBar = findViewById(R.id.deviceListAppBar)
        backArrowButton = findViewById(R.id.deviceListBackArrowButton)
        mainLinearLayout = findViewById(R.id.deviceListMainLinearLayout)
        space = findViewById(R.id.deviceListSpace)
        cancelButton = findViewById(R.id.deviceListCancelButton)
    }

    override fun onResume() {
        super.onResume()
        /**
         * Update activity
         */
        SessionState.activity = this
    }

    override fun onBackPressed() {
        finish()
    }
}