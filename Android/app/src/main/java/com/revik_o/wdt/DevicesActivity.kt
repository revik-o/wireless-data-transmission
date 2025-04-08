package com.revik_o.wdt

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
import com.revik_o.core.common.FetchOrSendType
import com.revik_o.core.common.OSType.ANDROID
import com.revik_o.core.common.OSType.LINUX
import com.revik_o.core.common.OSType.WINDOWS
import com.revik_o.core.common.RequestType
import com.revik_o.core.common.utils.ConcurrencyUtils.runConcurrentIOOperation
import com.revik_o.infrastructure.common.commands.fetch.DeviceInfoCommand
import com.revik_o.infrastructure.common.dtos.RemoteDeviceDto
import com.revik_o.infrastructure.common.utils.IPv4Utils.iterateOverNetwork
import com.revik_o.wdt.configs.AndroidAPI
import com.revik_o.wdt.definitions.IntentExtraApplicationKeys.FETCH_OR_SEND_KEY
import com.revik_o.wdt.definitions.IntentExtraApplicationKeys.REQUEST_TYPE_KEY
import com.revik_o.wdt.factories.ProtocolToolsFactory.createFetcher
import com.revik_o.wdt.listeners.button.DeviceOnClickListener
import kotlinx.coroutines.Job
import java.io.Serializable
import java.net.SocketTimeoutException

class DevicesActivity : AppCompatActivity() {

    private val _osApi = AndroidAPI(this)
    private lateinit var _concurrentContext: Job

    @SuppressLint("InflateParams")
    private fun createDeviceButton(device: RemoteDeviceDto): Button =
        (LayoutInflater.from(this).inflate(R.layout.device_button, null) as MaterialButton)
            .also { button ->
                when (device.os) {
                    LINUX -> button.icon = getDrawable(this, R.drawable.linux_24)
                    ANDROID -> button.icon = getDrawable(this, R.drawable.android_24)
                    WINDOWS -> button.icon = getDrawable(this, R.drawable.microsoft_windows_24)
                }

                button.text = device.title
            }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_devices)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val devicesList = findViewById<LinearLayout>(R.id.device_list)
        val progressBar = findViewById<ProgressBar>(R.id.device_search_progress_bar)
        val deviceSearchTitle = findViewById<TextView>(R.id.device_search_title)
        _concurrentContext = runConcurrentIOOperation {
            val fetcher = createFetcher(_osApi)
            val fetchOrSendType = intent.getFetchOrSendType()
            val requestType = intent.getRequestTypeType()

            iterateOverNetwork { ip ->
                try {
                    val device = fetcher.fetch(DeviceInfoCommand(ip))
                        ?: throw IllegalStateException()
                    DeviceOnClickListener(this, _osApi, device, requestType, fetchOrSendType)
                        .let { onClickListener ->
                            createDeviceButton(device).let { deviceButton ->
                                deviceButton.setOnClickListener(onClickListener)
                                runOnUiThread { devicesList.addView(deviceButton) }
                            }
                        }
                } catch (exception: SocketTimeoutException) {
                    Log.i(DevicesActivity::class.simpleName, "Skipped: $ip")
                    // ignore
                } catch (e: Exception) {
                    Log.e("WDT", e.toString())
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

private fun <T : Serializable> Intent.getSerializableExtraCompat(key: String, type: Class<T>): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, type)
    } else {
        type.cast(getSerializableExtra(key))
    }

private fun Intent.getFetchOrSendType(): FetchOrSendType =
    getSerializableExtraCompat(FETCH_OR_SEND_KEY, FetchOrSendType::class.java)
        ?: throw IllegalStateException()

private fun Intent.getRequestTypeType(): RequestType =
    getSerializableExtraCompat(REQUEST_TYPE_KEY, RequestType::class.java)
        ?: throw IllegalStateException()