package ua.edu.onaft.wirelessdatatransmission_wdt

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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onStart() {
        /**
         * Check Permissions
         */
        Method.checkPermissions()

        val viewPagerAdapter: FragmentStateAdapter = FileViewerViewPagerAdapter(this, this)
        fileViewerViewModel = FileViewerViewModel(viewPagerAdapter.itemCount)
        fileViewerViewModel.currentFile = Constant.mainExternalStorageDirectory!!
        fileViewerViewModel.inMainExternalStorage = false

        (viewPagerAdapter as FileViewerViewPagerAdapter).fileViewerViewModel = fileViewerViewModel
        viewPager.adapter = viewPagerAdapter
        floatingActionButton.setOnClickListener(FloatingActionButtonClickListener(this))
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)
        /**
         * Update activity
         */
        SessionState.activity = this
        /**
         * Getting activity things
         */
        viewPager = findViewById(R.id.fileViewerActivityViewPager)
        floatingActionButton = findViewById(R.id.fileViewerActivityFloatingActionButton)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBackPressed() {
        if (!fileViewerViewModel.turnToBack()) finish()
    }

}