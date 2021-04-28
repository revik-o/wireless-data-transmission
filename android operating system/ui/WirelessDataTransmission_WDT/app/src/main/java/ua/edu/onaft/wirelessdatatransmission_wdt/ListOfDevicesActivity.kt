package ua.edu.onaft.wirelessdatatransmission_wdt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateMargins

import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Action.BackArrowButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.ScreenDimension
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

class ListOfDevicesActivity : AppCompatActivity() {

    private lateinit var frameLayoutAppBar: FrameLayout
    private lateinit var backArrowButton: View
    private lateinit var mainLinearLayout: ViewGroup
    private lateinit var space: View
    private lateinit var cancelButton: View

    override fun onStart() {
        val screenDimension = ScreenDimension(this)
        val height = screenDimension.height
        val width = screenDimension.width

        frameLayoutAppBar.layoutParams.height = (height / 14.55).toInt()
        backArrowButton.layoutParams.height = (frameLayoutAppBar.layoutParams.height / 1.375).toInt()
        backArrowButton.layoutParams.width = backArrowButton.layoutParams.height
        (backArrowButton.layoutParams as FrameLayout.LayoutParams).updateMargins(left = width / 45, bottom = frameLayoutAppBar.layoutParams.height / 11)
        space.layoutParams.height = frameLayoutAppBar.layoutParams.height * 2 - 30

        backArrowButton.setOnClickListener(BackArrowButtonOnClickListener(this))

        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_devices)
        /**
         * Update activity
         */
        SessionState.activity = this
        frameLayoutAppBar = findViewById(R.id.deviceListAppBar)
        backArrowButton = findViewById(R.id.deviceListBackArrowButton)
        mainLinearLayout = findViewById(R.id.deviceListMainLinearLayout)
        space = findViewById(R.id.deviceListSpace)
        cancelButton = findViewById(R.id.deviceListCancelButton)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}