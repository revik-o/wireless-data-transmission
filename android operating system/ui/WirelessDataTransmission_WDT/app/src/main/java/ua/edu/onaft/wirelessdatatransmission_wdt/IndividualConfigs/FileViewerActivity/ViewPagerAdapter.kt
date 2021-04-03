package ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.Fragments.FileViewerFragment
import ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.Fragments.FileViewerListOfSelectedFilesFragment

class ViewPagerAdapter(activity: Activity, fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    val activity: Activity = activity

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FileViewerFragment(this.activity)
            else -> FileViewerListOfSelectedFilesFragment()
        }
    }
}