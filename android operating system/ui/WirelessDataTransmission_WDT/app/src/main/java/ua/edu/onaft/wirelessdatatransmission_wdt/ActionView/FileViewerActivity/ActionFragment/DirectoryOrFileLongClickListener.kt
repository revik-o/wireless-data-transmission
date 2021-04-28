package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment

import android.app.Activity
import android.util.TypedValue
import android.view.View
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.CustomFrameLayout
import ua.edu.onaft.wirelessdatatransmission_wdt.R
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel

class DirectoryOrFileLongClickListener(activity: Activity, fileViewerViewModel: FileViewerViewModel, customFrameLayout: CustomFrameLayout): View.OnLongClickListener {

//    private val activity: Activity = activity
    private val fileViewerViewModel: FileViewerViewModel = fileViewerViewModel
    private val customFrameLayout: CustomFrameLayout = customFrameLayout

    override fun onLongClick(v: View?): Boolean {
        if (fileViewerViewModel.onModeChoose)
            fileViewerViewModel.open(customFrameLayout.file)
        else {
            fileViewerViewModel.onModeChoose = true
            customFrameLayout.isChosen = true
            fileViewerViewModel.addChosenFile(customFrameLayout.file)
        }
        return true
    }

}