package com.revik_o.wdt

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.revik_o.core.common.utils.ConcurrencyUtils.runConcurrentIOOperation
import com.revik_o.infrastructure.common.commands.fetch.DeviceInfoCommand
import com.revik_o.infrastructure.common.utils.IPv4Utils.iterateOverNetwork
import com.revik_o.infrastructure.tcp.TCPFetcher
import com.revik_o.wdt.component.DeviceButtonComponent
import com.revik_o.wdt.configs.AndroidAPI

class DevicesActivity : AppCompatActivity() {

    private lateinit var _deviceListElement: LinearLayout

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

        val activityContextPtr = this
        _deviceListElement = findViewById(R.id.device_list)

        findViewById<ProgressBar>(R.id.device_search_progress_bar).let { progressBar ->
            runConcurrentIOOperation {

            val fetcher = TCPFetcher(AndroidAPI())
            val device = fetcher.fetch(DeviceInfoCommand("192.168.50.65"))
                ?: throw RuntimeException()

            runOnUiThread {
                Log.d("CHECK", "192.168.50.65" + "!!!!!")
                _deviceListElement.addView(
                    DeviceButtonComponent(
                        activityContextPtr,
                        device
                    ) {
                        Toast.makeText(
                            activityContextPtr,
                            "Working with ${it.title}...",
                            Toast.LENGTH_SHORT
                        ).show()
                        openDownloadActivityAlert().show()
                    })
            }

//                iterateOverNetwork { ip ->
//                    try {
//                        val device = fetcher.fetch(DeviceInfoCommand(ip))
//                            ?: throw RuntimeException()
//
//                        runOnUiThread {
//                            Log.d("CHECK", ip + "!!!!!")
//                            _deviceListElement.addView(
//                                DeviceButtonComponent(
//                                    activityContextPtr,
//                                    device
//                                ) {
//                                    Toast.makeText(
//                                        activityContextPtr,
//                                        "Working with ${it.title}...",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    openDownloadActivityAlert().show()
//                                })
//                        }
//                    } catch (ignore: Exception) {
//                        ignore.message?.let { Log.d("CHECK", it) }
//                        // ignore
//                    }
//                }
                runOnUiThread {
                    progressBar.isEnabled = false
                    progressBar.alpha = 0.0f
                }
            }
//            }
        }
    }
}