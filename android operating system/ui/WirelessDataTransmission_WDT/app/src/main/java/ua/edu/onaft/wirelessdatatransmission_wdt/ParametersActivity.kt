package ua.edu.onaft.wirelessdatatransmission_wdt

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.WDTComponents.AppOption
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.ParametersActivity.HistoryButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Action.BackArrowButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

class ParametersActivity : AppCompatActivity() {

    private lateinit var backArrowButton: View
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switch: Switch
    private lateinit var deviceNameButton: Button
    private lateinit var historyButton: View

    override fun onStart() {
        backArrowButton.setOnClickListener(BackArrowButtonOnClickListener(this))

        deviceNameButton.text = AppOption.LOCAL_DEVICE_NAME

        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)
        /**
         * Getting activity things
         */
        backArrowButton = findViewById(R.id.settingsActivityBackArrowButton)
        switch = findViewById(R.id.settingsActivitySwitch)
        deviceNameButton = findViewById(R.id.settingsActivityDeviceName)
        historyButton = findViewById(R.id.settingsActivityHistory)

        historyButton.setOnClickListener(HistoryButtonOnClickListener(this))
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