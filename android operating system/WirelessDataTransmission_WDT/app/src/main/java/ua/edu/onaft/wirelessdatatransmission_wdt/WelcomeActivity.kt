package ua.edu.onaft.wirelessdatatransmission_wdt

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.CommonMethod
import ua.edu.onaft.wirelessdatatransmission_wdt.Configuration.DefaultApplicationConfig
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState

class WelcomeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        /**
         * Config button
         */
        val startButton: Button = findViewById(R.id.startButton)
        val startButton1: Button = findViewById(R.id.startButton1)
        startButton.setOnClickListener(StartButtonOnClickListener())
        startButton1.setOnClickListener(StartButton1OnClickListener())

        StaticState.activity = this

        /**
         * Check permissions
         */
        CommonMethod().checkPermissions()
        /**
         * Load config
         */
        DefaultApplicationConfig()
    }

    /**
     * On Click Listener
     */
    private class StartButtonOnClickListener: View.OnClickListener {
        override fun onClick(view: View?) {
            StaticState.activity.startActivity(Intent(StaticState.activity.applicationContext, FileViewerActivity2::class.java))
        }
    }

    private class StartButton1OnClickListener: View.OnClickListener {
        override fun onClick(v: View?) {}
    }

}