package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity

import android.app.Activity
import android.content.Intent
import android.view.View

import ua.edu.onaft.wirelessdatatransmission_wdt.ListOfDevicesActivity

class FloatingActionButtonClickListener(activity: Activity): View.OnClickListener {

    val activity: Activity = activity

    override fun onClick(v: View?) {
        activity.startActivity(Intent(activity, ListOfDevicesActivity::class.java))
    }

}