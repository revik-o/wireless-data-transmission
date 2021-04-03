package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Space
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.updateMargins
import androidx.drawerlayout.widget.DrawerLayout

import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity.GetClipboardClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity.SendDataClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity.SendDataFromGoogleDriveClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity.SideMenuButtonClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Commont.ScreenDimension

class WelcomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBar: FrameLayout
    private lateinit var sideMenuButton: ImageView
    private lateinit var space: Space

    // Send Data
    private lateinit var sendDataFrameLayout: FrameLayout
    private lateinit var sendDataImageView: ImageView

    // Send Data From Google Drive
    private lateinit var sendDataFromGoogleDriveFrameLayout: FrameLayout
    private lateinit var sendDataFromGoogleDriveImageView: ImageView
    private lateinit var sendDataFromGoogleDriveImageViewGoogleDrive: ImageView

    // Get Clipboard
    private lateinit var getClipboardFrameLayout: FrameLayout
    private lateinit var getClipboardImageView: ImageView

    override fun onStart() {
        /**
         * Configuring activity
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            drawerLayout = findViewById(R.id.welcomeActivityDrawerLayout)
            val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
        }
        sideMenuButton.setOnClickListener(SideMenuButtonClickListener(this, drawerLayout))
        sendDataFrameLayout.setOnClickListener(SendDataClickListener(this, sendDataFrameLayout))
        sendDataFromGoogleDriveFrameLayout.setOnClickListener(SendDataFromGoogleDriveClickListener(this, sendDataFromGoogleDriveFrameLayout))
        getClipboardFrameLayout.setOnClickListener(GetClipboardClickListener(this, getClipboardFrameLayout))

        /**
         * adapting activity
         */
        val screenDimension = ScreenDimension(this)
        var width: Int = screenDimension.width
        var height: Int = screenDimension.height
        // appBar
        appBar.layoutParams.height = (height / 11.55).toInt()
        // sideMenuButton
        sideMenuButton.layoutParams.width = (width / 12)
        sideMenuButton.layoutParams.height = sideMenuButton.layoutParams.width
        (sideMenuButton.layoutParams as FrameLayout.LayoutParams).setMargins(width / 45, width / 45, 0, 0)
        // space
        space.layoutParams.height = (appBar.layoutParams.height * 1.3).toInt()

        val frameLayoutHeight = (height / 4.93).toInt()
        val imageViewHeight = (frameLayoutHeight * 1.111).toInt()
        val imageViewWidth = imageViewHeight
        val imageViewMarginLeft = 20.5
        val imageViewMarginTop = 15.5

        // Send Data
        // sendDataFrameLayout
        sendDataFrameLayout.layoutParams.height = frameLayoutHeight
        // sendDataImageView
        sendDataImageView.layoutParams.height = imageViewHeight
        sendDataImageView.layoutParams.width = imageViewWidth
        (sendDataImageView.layoutParams  as FrameLayout.LayoutParams).updateMargins(top = (frameLayoutHeight / imageViewMarginTop).toInt(), right = -(width / imageViewMarginLeft).toInt())

        // Send Data From Google Drive
        // sendDataFromGoogleDriveFrameLayout
        sendDataFromGoogleDriveFrameLayout.layoutParams.height = frameLayoutHeight
        // sendDataFromGoogleDriveImageView
        sendDataFromGoogleDriveImageView.layoutParams.height = imageViewHeight
        sendDataFromGoogleDriveImageView.layoutParams.width = imageViewWidth
        (sendDataFromGoogleDriveImageView.layoutParams as FrameLayout.LayoutParams).updateMargins(top = (frameLayoutHeight / imageViewMarginTop).toInt(), right = -(width / imageViewMarginLeft).toInt())
        // sendDataFromGoogleDriveImageViewGoogleDrive
        sendDataFromGoogleDriveImageViewGoogleDrive.layoutParams.height = (frameLayoutHeight / 4.5).toInt()
        sendDataFromGoogleDriveImageViewGoogleDrive.layoutParams.width = sendDataFromGoogleDriveImageViewGoogleDrive.layoutParams.height
        (sendDataFromGoogleDriveImageViewGoogleDrive.layoutParams as FrameLayout.LayoutParams).updateMargins(left = (width / 110.5).toInt(), top = (frameLayoutHeight / 1.42).toInt())

        // Get Clipboard
        // getClipboardFrameLayout
        getClipboardFrameLayout.layoutParams.height = frameLayoutHeight
        // getClipboardImageView
        getClipboardImageView.layoutParams.height = imageViewHeight
        getClipboardImageView.layoutParams.width = imageViewWidth
        (getClipboardImageView.layoutParams as FrameLayout.LayoutParams).updateMargins(top = (frameLayoutHeight / imageViewMarginTop).toInt(), right = -(width / imageViewMarginLeft).toInt())
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        /**
         * Getting activity things
         */
        appBar = findViewById(R.id.welcomeActivityAppBar)
        sideMenuButton = findViewById(R.id.welcomeActivityAppBarSideMenu)
        space = findViewById(R.id.welcomeActivitySpace)
        // Send Data
        sendDataFrameLayout = findViewById(R.id.welcomeActivitySendData)
        sendDataImageView = findViewById(R.id.welcomeActivitySendDataImageView)

        // Send Data From Google Drive
        sendDataFromGoogleDriveFrameLayout = findViewById(R.id.welcomeActivitySendDataFromGoogleDrive)
        sendDataFromGoogleDriveImageView = findViewById(R.id.welcomeActivitySendDataFromGoogleDriveImageView)
        sendDataFromGoogleDriveImageViewGoogleDrive = findViewById(R.id.welcomeActivitySendDataFromGoogleDriveImageViewGoogleDrive)

        // Get Clipboard
        getClipboardFrameLayout = findViewById(R.id.welcomeActivityGetClipboard)
        getClipboardImageView = findViewById(R.id.welcomeActivityGetClipboardImageView)
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else super.onBackPressed()
        }
        else super.onBackPressed()
    }

}