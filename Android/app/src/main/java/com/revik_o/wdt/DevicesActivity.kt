package com.revik_o.wdt

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.revik_o.common.factory.IntentFactory.createIntentCompat
import com.revik_o.core.common.FetchOrSendType
import com.revik_o.core.common.OSType.ANDROID
import com.revik_o.core.common.OSType.LINUX
import com.revik_o.core.common.OSType.WINDOWS
import com.revik_o.core.common.RequestType
import com.revik_o.core.common.utils.ConcurrencyUtils.runConcurrentIOOperation
import com.revik_o.impl.AndroidAPI
import com.revik_o.impl.AndroidAPI.Companion.APPLICATION_LOG_TAG
import com.revik_o.impl.factory.ProtocolToolsFactory.createFetcher
import com.revik_o.infrastructure.common.commands.fetch.DeviceInfoCommand
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto
import com.revik_o.infrastructure.common.utils.IPv4Utils.iterateOverNetwork
import com.revik_o.wdt.listeners.button.DeviceOnClickListener
import kotlinx.coroutines.Job
import java.net.SocketTimeoutException

class DevicesActivity : AppCompatActivity() {

    private lateinit var _osApi: AndroidAPI
    private lateinit var _concurrentContext: Job

    @SuppressLint("InflateParams")
    private fun createDeviceButton(device: RemoteDeviceDto): Button =
        (LayoutInflater.from(this).inflate(R.layout.device_button, null) as MaterialButton)
            .also { button ->
                when (device.os) {
                    LINUX -> button.icon = getDrawable(this, R.drawable.linux_100)
                    ANDROID -> button.icon = getDrawable(this, R.drawable.android_100)
                    WINDOWS -> button.icon = getDrawable(this, R.drawable.microsoft_windows_100)
                }

                button.text = device.title
            }

    private fun deviceOnClickListener(
        device: RemoteDeviceDto,
        requestType: RequestType,
        fetchOrSendType: FetchOrSendType,
        resources: Array<out Uri>
    ): OnClickListener = DeviceOnClickListener(
        this,
        _osApi,
        device,
        requestType,
        fetchOrSendType,
        resources
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_devices)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _osApi = AndroidAPI(this)
        val devicesList = findViewById<LinearLayout>(R.id.device_list)
        val progressBar = findViewById<ProgressBar>(R.id.device_search_progress_bar)
        val deviceSearchTitle = findViewById<TextView>(R.id.device_search_title)
        _concurrentContext = runConcurrentIOOperation {
            val intent = createIntentCompat(intent)
            val fetcher = createFetcher(_osApi)
            val fetchOrSendType = intent.getFetchOrSendType()
            val requestType = intent.getRequestTypeType()
            val resources = intent.getResourceSequence().toTypedArray()

            iterateOverNetwork { ip ->
                try {
                    val device = fetcher.fetch(DeviceInfoCommand(ip))
                        ?: throw IllegalStateException()
                    val deviceButton = createDeviceButton(device)

                    deviceButton.setOnClickListener(
                        deviceOnClickListener(device, requestType, fetchOrSendType, resources)
                    )

                    runOnUiThread { devicesList.addView(deviceButton) }
                } catch (exception: SocketTimeoutException) {
                    Log.i(APPLICATION_LOG_TAG, "Skipped: $ip")
                    // ignore
                } catch (e: Exception) {
                    Log.e(APPLICATION_LOG_TAG, e.toString())
                }
            }
            runOnUiThread {
                deviceSearchTitle.text = "Devices" // FIXME
                progressBar.isEnabled = false
                progressBar.alpha = 0.0f
            }
        }
    }

    override fun onStop() {
        if (_concurrentContext.isActive) {
            _concurrentContext.cancel()
        }

        super.onStop()
    }
}