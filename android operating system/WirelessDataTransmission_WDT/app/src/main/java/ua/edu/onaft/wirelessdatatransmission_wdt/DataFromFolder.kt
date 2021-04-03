package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.CommonMethod

import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerActivity.FileViewerActivityViewModel

@Deprecated("")
class DataFromFolder : AppCompatActivity() {

    lateinit var linearLayout: LinearLayout

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_from_folder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Chose File"

        linearLayout = findViewById(R.id.dataFromFolderLinearLayout)

        StaticState.activity = this

        /**
         * Check permissions
         */
        CommonMethod().checkPermissions()

        /**
         * Setup ViewModel
         */
//        FileViewerActivityViewModel().addViewGroup(linearLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}