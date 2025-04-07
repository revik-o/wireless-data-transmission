package com.revik_o.wdt

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.revik_o.core.service.DeviceCommunicationServiceI
import com.revik_o.core.util.AsyncUtils.runAsync
import com.revik_o.infrastructure.ip.v4.scanNetwork
import com.revik_o.wdt.component.DeviceButtonComponent

class DevicesActivity : AppCompatActivity() {

    private lateinit var _deviceListElement: LinearLayout
    private lateinit var _deviceCommunicationService: DeviceCommunicationServiceI

    private fun openDownloadActivityAlert(): AlertDialog =
        AlertDialog.Builder(this).setMessage("Open downloads?")
            .setPositiveButton("Open") { dialog, _ ->
                startActivity(Intent(this, DevicesActivity::class.java))
                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .create() ?: throw IllegalStateException("Activity cannot be null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_devices)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _deviceListElement = findViewById(R.id.device_list)

        runAsync {
            scanNetwork(_deviceCommunicationService) { remoteDeviceDto ->
                runOnUiThread {
                    _deviceListElement.addView(DeviceButtonComponent(this, remoteDeviceDto) {
                        Toast.makeText(this, "Working with ${it.deviceName}...", Toast.LENGTH_SHORT)
                            .show()
                        openDownloadActivityAlert().show()
                    })
                }
            }
        }
    }
}