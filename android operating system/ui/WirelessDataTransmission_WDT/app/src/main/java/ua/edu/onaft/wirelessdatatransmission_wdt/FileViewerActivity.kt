package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.floatingactionbutton.FloatingActionButton

import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.FloatingActionButtonClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.ViewPagerAdapter

class FileViewerActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var floatingActionButton: FloatingActionButton

    override fun onStart() {
        val viewPagerAdapter: FragmentStateAdapter = ViewPagerAdapter(this, this)
        viewPager.adapter = viewPagerAdapter

        floatingActionButton.setOnClickListener(FloatingActionButtonClickListener(this))
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)
        /**
         * Getting activity things
         */
        viewPager = findViewById(R.id.fileViewerActivityViewPager)
        floatingActionButton = findViewById(R.id.fileViewerActivityFloatingActionButton)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}