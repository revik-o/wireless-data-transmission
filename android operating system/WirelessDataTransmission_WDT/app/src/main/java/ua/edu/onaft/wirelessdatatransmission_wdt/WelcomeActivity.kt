package ua.edu.onaft.wirelessdatatransmission_wdt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val startButton: Button = findViewById(R.id.startButton)
        startButton.setOnClickListener(ButtonActions.WelcomeActivity.StartActionListener())
    }
}