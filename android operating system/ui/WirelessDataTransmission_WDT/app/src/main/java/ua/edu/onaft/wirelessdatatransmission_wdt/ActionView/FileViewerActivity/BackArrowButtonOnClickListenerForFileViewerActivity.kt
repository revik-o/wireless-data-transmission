package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity

import android.app.Activity
import android.view.View
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

class BackArrowButtonOnClickListenerForFileViewerActivity(activity: Activity): View.OnClickListener {

    private val activity: Activity = activity

    override fun onClick(v: View?) {
        SessionState.choosenFiles.clear()
        activity.finish()
    }
}