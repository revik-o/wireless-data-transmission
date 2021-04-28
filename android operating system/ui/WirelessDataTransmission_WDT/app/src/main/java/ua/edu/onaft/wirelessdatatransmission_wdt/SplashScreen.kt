package ua.edu.onaft.wirelessdatatransmission_wdt

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import ua.edu.onaft.wirelessdatatransmission_wdt.Configuration.DefaultApplicationConfig

class SplashScreen : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    private fun config() {
        /**
         * Update activity
         */
        SessionState.activity = this
        /**
         * Check Permissions
         */
        Method.checkPermissions()
        /**
         * External Storage
         */
        Constant.mainExternalStorageDirectory = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R)
            Environment.getExternalStorageDirectory()
        else
            applicationContext.getExternalFilesDir("/")
        /**
         * Start Service
         */
        DefaultApplicationConfig()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        SessionState.activity = this
        config()
        startActivity(Intent(this, WelcomeActivity::class.java))
    }

}