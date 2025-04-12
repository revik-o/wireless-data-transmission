package com.revik_o.wdt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.revik_o.common.factory.IntentFactory.createIntentCompat

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
                startActivity(createIntentCompat(this, DevicesActivity::class.java).sendClipboard())
            }
        }
        findViewById<Button>(R.id.get_from_remote_device_button).let { button ->
            button.setOnClickListener {
                startActivity(
                    createIntentCompat(this, DevicesActivity::class.java)
                        .getRemoteClipboard()
                )
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