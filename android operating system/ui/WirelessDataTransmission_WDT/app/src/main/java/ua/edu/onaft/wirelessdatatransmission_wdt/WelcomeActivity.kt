package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity.GetClipboardClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity.SendDataClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity.SettingsMenuButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

class WelcomeActivity : AppCompatActivity() {

    private lateinit var settingsMenuButton: ImageView

    // Send Data
    private lateinit var sendDataFrameLayout: FrameLayout

    // Send Data From Google Drive
    private lateinit var sendDataFromGoogleDriveFrameLayout: FrameLayout

    // Get Clipboard
    private lateinit var getClipboardFrameLayout: FrameLayout

    override fun onStart() {
        /**
         * Configuring activity
         */
        settingsMenuButton.setOnClickListener(SettingsMenuButtonOnClickListener(this))
        sendDataFrameLayout.setOnClickListener(SendDataClickListener(this, sendDataFrameLayout))
        getClipboardFrameLayout.setOnClickListener(GetClipboardClickListener(this, getClipboardFrameLayout))

        /**
         * adapting activity
         */
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_welcome_scroll_view)
        /**
         * Getting activity things
         */
        settingsMenuButton = findViewById(R.id.welcomeActivityAppBarSettingsMenu)
        // Send Data
        sendDataFrameLayout = findViewById(R.id.welcomeActivitySendData)


        // Get Clipboard
        getClipboardFrameLayout = findViewById(R.id.welcomeActivityGetClipboard)
    }

    override fun onResume() {
        super.onResume()
        /**
         * Update activity
         */
        SessionState.context = this

        Method.cleanArrayOfFiles()
    }

}