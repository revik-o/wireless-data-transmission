package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity

import android.app.Activity
import android.content.Intent
import android.view.View
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState

import ua.edu.onaft.wirelessdatatransmission_wdt.ListOfDevicesActivity

class FloatingActionButtonClickListener(activity: Activity): View.OnClickListener {

    private val activity: Activity = activity

    override fun onClick(v: View?) {
        if (SessionState.choosenFiles.size != 0)
            activity.startActivity(Intent(activity, ListOfDevicesActivity::class.java))
    }

}