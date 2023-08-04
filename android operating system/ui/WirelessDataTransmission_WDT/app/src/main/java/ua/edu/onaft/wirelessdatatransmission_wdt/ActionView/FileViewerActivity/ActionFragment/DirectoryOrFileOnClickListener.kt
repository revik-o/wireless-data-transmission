package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.CustomFrameLayout
import ua.edu.onaft.wirelessdatatransmission_wdt.R
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel

class DirectoryOrFileOnClickListener(activity: Activity, fileViewerViewModel: FileViewerViewModel, customFrameLayout: CustomFrameLayout): View.OnClickListener {

    private val activity: Activity = activity
    private val fileViewerViewModel: FileViewerViewModel = fileViewerViewModel
    private val customFrameLayout: CustomFrameLayout = customFrameLayout

    override fun onClick(v: View?) {
        if (fileViewerViewModel.onModeChoose) {
            if (customFrameLayout.isChosen) {
                fileViewerViewModel.removeChosenFile(customFrameLayout.file)
                customFrameLayout.isChosen = false
            } else {
                fileViewerViewModel.addChosenFile(customFrameLayout.file)
                customFrameLayout.isChosen = true
            }
        } else {
            customFrameLayout.frameLayout.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.custom_button_click_animation))
            fileViewerViewModel.open(customFrameLayout.file)
        }
    }

}