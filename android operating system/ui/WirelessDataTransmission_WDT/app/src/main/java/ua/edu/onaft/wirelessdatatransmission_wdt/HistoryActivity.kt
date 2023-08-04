package ua.edu.onaft.wirelessdatatransmission_wdt

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class HistoryActivity : AppCompatActivity() {

    private lateinit var acceptedFileHistoryButton: Button
    private lateinit var trustedFileHistoryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        acceptedFileHistoryButton = findViewById<Button>(R.id.acceptedFileHistoryButton)
        trustedFileHistoryButton = findViewById<Button>(R.id.trustedFileHistoryButton)
        acceptedFileHistoryButton.setOnClickListener(AcceptFileHistoryButtonOnClickListener(this))
        trustedFileHistoryButton.setOnClickListener(TrustedFileHistoryButtonOnClickListener(this))
    }

    private class AcceptFileHistoryButtonOnClickListener(activity: Activity): View.OnClickListener {

        val activity: Activity = activity

        override fun onClick(v: View?) {
            activity.startActivity(Intent(activity, AcceptedFileHistoryActivity::class.java))
        }

    }

    private class TrustedFileHistoryButtonOnClickListener(activity: Activity): View.OnClickListener {

        val activity: Activity = activity

        override fun onClick(v: View?) {
            activity.startActivity(Intent(activity, TrustedFileHistoryActivity::class.java))
        }

    }

}