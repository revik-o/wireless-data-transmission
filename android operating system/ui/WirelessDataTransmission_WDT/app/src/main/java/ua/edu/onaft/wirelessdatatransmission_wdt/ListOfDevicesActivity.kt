package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateMargins
import com.WDTComponents.AppConfig
import com.WDTComponents.DelegateMethods.IDelegateMethodIntegerArg
import com.WDTComponents.DelegateMethods.IDelegateMethodSocketAction
import com.WDTComponents.WorkingWithDevices.ScanDevicesIPVersion4
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.ListOfDevicesActivity.CancelButtonOnClickListener
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
    private lateinit var progressBar: ProgressBar
    private lateinit var cancelButton: View
    private lateinit var scanDeviceService: ScanDevicesIPVersion4

    private fun createButton(typeDevice: String, nameDevice: String): Button {
        val deviceButton: Button = DeviceButton(this@ListOfDevicesActivity, typeDevice, nameDevice).button
        runOnUiThread {
            mainLinearLayout.addView(deviceButton)
        }
        return deviceButton
    }

    fun mode(modeId: Int): IDelegateMethodSocketAction {
        return when (modeId) {
            0 -> object : IDelegateMethodSocketAction {
                override fun voidMethod(nameDevice: String, typeDevice: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, socket: Socket) {
                    createButton(typeDevice, nameDevice).setOnClickListener(DeviceButtonOnClickListener(socket))
                }
            }
            1 -> object : IDelegateMethodSocketAction {
                override fun voidMethod(nameDevice: String, typeDevice: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, socket: Socket) {
                    createButton(typeDevice, nameDevice).setOnClickListener {
                        Thread {
                            AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType3(socket)
                        }.start()
                    }
                }
            }
            else -> object : IDelegateMethodSocketAction {
                override fun voidMethod(nameDevice: String, typeDevice: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, socket: Socket) {}
            }
        }
    }

    private fun progressBarProgress(): IDelegateMethodIntegerArg {
        return object : IDelegateMethodIntegerArg {
            override fun voidMethod(number: Int) {
                runOnUiThread {
                    progressBar.progress = number
                }
            }
        }
    }

    override fun onStart() {
        val screenDimension = ScreenDimension(this)
        val height = screenDimension.height
        val width = screenDimension.width

//        frameLayoutAppBar.layoutParams.height = (height / 14.55).toInt()
//        backArrowButton.layoutParams.height = (frameLayoutAppBar.layoutParams.height / 1.375).toInt()
//        backArrowButton.layoutParams.width = backArrowButton.layoutParams.height
//        (backArrowButton.layoutParams as FrameLayout.LayoutParams).updateMargins(left = width / 45, bottom = frameLayoutAppBar.layoutParams.height / 11)
//        progressBar.progress = 0
//        space.layoutParams.height = Constant.usualSpace

        backArrowButton.setOnClickListener(BackArrowButtonOnClickListener(this))

        scanDeviceService = ScanDevicesIPVersion4(mode(SessionState.sendType), progressBarProgress())
        cancelButton.setOnClickListener(CancelButtonOnClickListener(scanDeviceService))
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_devices)
        frameLayoutAppBar = findViewById(R.id.deviceListAppBar)
        backArrowButton = findViewById(R.id.deviceListBackArrowButton)
        mainLinearLayout = findViewById(R.id.deviceListMainLinearLayout)
        progressBar = findViewById(R.id.deviceListProgressBar)
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