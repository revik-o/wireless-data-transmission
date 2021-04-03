package ua.edu.onaft.wirelessdatatransmission_wdt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

import com.WDTComponents.AppConfig
import com.WDTComponents.DelegateMethods.IDelegateMethod
import com.WDTComponents.DelegateMethods.IDelegateMethodIntegerArg
import com.WDTComponents.DelegateMethods.IDelegateMethodSocketAction
import com.WDTComponents.WorkingWithDevices.ScanDevicesIPVersion4

import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class ScanDevise : AppCompatActivity() {

    private var linearLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_scan_devise)

        StaticState.activity = this

        ScanDevicesIPVersion4(
                object : IDelegateMethodSocketAction {
                    override fun voidMethod(nameDevice: String, typeDevice: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, socket: Socket) {
                        try {
                            runOnUiThread {
                                val button = Button(this@ScanDevise)
                                button.text = nameDevice
                                button.setOnClickListener(B(socket))
                                linearLayout?.addView(button)
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                },
                object : IDelegateMethodIntegerArg {
                    override fun voidMethod(number: Int) {
                    }
                }
        )
    }

    class B(socket: Socket): View.OnClickListener {
        val socket = socket
        override fun onClick(v: View?) {
            var sendType = 0
            for (file in StaticState.fileSet!!) {
                if (file.isDirectory) {
                    sendType = 2
                    break
                }
                else sendType = 1
            }
            if (sendType == 1) {
                AppConfig.ThreadType.iUsualThread.execute(object : IDelegateMethod {
                    override fun voidMethod() {
                        AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType1(socket, StaticState.fileSet!!)
                    }
                })
            } else if (sendType == 2) {
                AppConfig.ThreadType.iUsualThread.execute(object : IDelegateMethod {
                    override fun voidMethod() {
                        AppConfig.Action.SendTypeInterface.iActionForSendType.clientActionForSendType2(socket, StaticState.fileSet!!)
                    }
                })
            }
        }
    }

}