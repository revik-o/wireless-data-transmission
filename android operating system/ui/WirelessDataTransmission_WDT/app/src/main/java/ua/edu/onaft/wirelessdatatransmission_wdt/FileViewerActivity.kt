package ua.edu.onaft.wirelessdatatransmission_wdt

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.FloatingActionButtonClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.FileViewerViewPagerAdapter
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel

class FileViewerActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var fileViewerViewModel: FileViewerViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)
        /**
         * Getting activity things
         */
        viewPager = findViewById(R.id.fileViewerActivityViewPager)
        floatingActionButton = findViewById(R.id.fileViewerActivityFloatingActionButton)

        /**
         * Check Permissions
         */
        Method.checkPermissions(this)

        val viewPagerAdapter: FragmentStateAdapter = FileViewerViewPagerAdapter(this, this)
        fileViewerViewModel = FileViewerViewModel(this, viewPagerAdapter.itemCount)
        if (Constant.mainExternalStorageDirectory != null)
            fileViewerViewModel.currentFile = Constant.mainExternalStorageDirectory!!
        else
            startActivity(Intent(this, SplashScreen::class.java))
        fileViewerViewModel.inMainExternalStorage = false

        (viewPagerAdapter as FileViewerViewPagerAdapter).fileViewerViewModel = fileViewerViewModel
        viewPager.adapter = viewPagerAdapter
        floatingActionButton.setOnClickListener(FloatingActionButtonClickListener(this))
    }

    override fun onResume() {
        super.onResume()
        /**
         * Update activity
         */
        SessionState.context = this

        SessionState.sendType = 0
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBackPressed() {
        if (!fileViewerViewModel.turnToBack()) finish()
    }

}