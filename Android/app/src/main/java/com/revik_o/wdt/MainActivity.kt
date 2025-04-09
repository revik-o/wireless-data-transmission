package com.revik_o.wdt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.revik_o.core.common.FetchOrSendType.FETCH
import com.revik_o.core.common.FetchOrSendType.SEND
import com.revik_o.core.common.RequestType.CLIPBOARD
import com.revik_o.wdt.definitions.IntentExtraApplicationKeys.FETCH_OR_SEND_KEY
import com.revik_o.wdt.definitions.IntentExtraApplicationKeys.REQUEST_TYPE_KEY

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.send_request_button).let { button ->
            button.setOnClickListener {
                startActivity(Intent(this, DevicesActivity::class.java).sendClipboard())
            }
        }
        findViewById<Button>(R.id.get_from_remote_device_button).let { button ->
            button.setOnClickListener {
                startActivity(Intent(this, DevicesActivity::class.java).getRemoteClipboard())
            }
        }
        findViewById<Button>(R.id.settings_button).let { button ->
            button.setOnClickListener {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            button.setEnabled(false)
            button.alpha = 0.0f
        }
        findViewById<Button>(R.id.downloads_button).let { button ->
            button.setEnabled(false)
            button.alpha = 0.0f
        }
        findViewById<Button>(R.id.about_app_button).let { button ->
            button.setOnClickListener {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
    }
}

private fun Intent.sendClipboard(): Intent {
    putExtra(FETCH_OR_SEND_KEY, SEND)
    putExtra(REQUEST_TYPE_KEY, CLIPBOARD)
    return this
}

private fun Intent.getRemoteClipboard(): Intent {
    putExtra(FETCH_OR_SEND_KEY, FETCH)
    putExtra(REQUEST_TYPE_KEY, CLIPBOARD)
    return this
}