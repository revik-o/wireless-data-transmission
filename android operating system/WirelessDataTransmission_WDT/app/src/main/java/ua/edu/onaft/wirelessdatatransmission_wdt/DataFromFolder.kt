package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Bundle
import android.os.Environment
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class DataFromFolder : AppCompatActivity() {

    lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_from_folder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Chose File"

        linearLayout = findViewById(R.id.dataFromFolderLinearLayout)

        val mainDirectory = File(Environment.getExternalStorageDirectory().absolutePath)
        for (file in mainDirectory.listFiles()) {
            val textView = TextView(this);
            textView.text = file.name
            linearLayout.addView(textView)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}