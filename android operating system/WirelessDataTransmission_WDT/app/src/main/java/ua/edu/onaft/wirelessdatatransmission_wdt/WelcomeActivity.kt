package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi

import ua.edu.onaft.wirelessdatatransmission_wdt.Configuration.DefaultApplicationConfig
import ua.edu.onaft.wirelessdatatransmission_wdt.State.StaticState

class WelcomeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener(ButtonActions.WelcomeActivity.StartActionListener(this))
        requestPermissions(arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.SYSTEM_ALERT_WINDOW
        ), 1)
        /**
         * Load configurations
         */
        StaticState.activity = this
        DefaultApplicationConfig()
    }

}