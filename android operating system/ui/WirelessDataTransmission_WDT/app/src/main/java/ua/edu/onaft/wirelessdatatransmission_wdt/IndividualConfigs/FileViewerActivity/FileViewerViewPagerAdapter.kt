package ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity

import android.app.Activity
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
import ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.Fragments.FileViewerFragment
import ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.Fragments.FileViewerListOfSelectedFilesFragment
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel

class FileViewerViewPagerAdapter(activity: Activity, fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val activity: Activity = activity
    private val linearLayoutForFileViewerFragment = LinearLayout(activity)
    private val linearLayoutForFileViewerListOfSelectedFilesFragment = LinearLayout(activity)
    lateinit var fileViewerViewModel: FileViewerViewModel

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                /**
                 * First init
                 */
                linearLayoutForFileViewerFragment.orientation = LinearLayout.VERTICAL
                linearLayoutForFileViewerListOfSelectedFilesFragment.orientation = LinearLayout.VERTICAL
                Method.addInLinearLayoutNewSpace(activity, linearLayoutForFileViewerListOfSelectedFilesFragment, Constant.usualSpace)
                /**
                 * Start Load
                 */
                fileViewerViewModel.setLinearLayout(linearLayoutForFileViewerFragment)
                fileViewerViewModel.setLinearLayout(linearLayoutForFileViewerListOfSelectedFilesFragment)
                FileViewerFragment(this.activity, fileViewerViewModel, linearLayoutForFileViewerFragment)
            }
            else -> {
                FileViewerListOfSelectedFilesFragment(this.activity, fileViewerViewModel, linearLayoutForFileViewerListOfSelectedFilesFragment)
            }
        }
    }

}