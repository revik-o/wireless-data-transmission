package ua.edu.onaft.wirelessdatatransmission_wdt.Common.Action

import android.app.Activity
import android.view.View

class BackArrowButtonOnClickListener(activity: Activity): View.OnClickListener {

    val activity: Activity = activity

    override fun onClick(v: View?) {
        back(this.activity)
    }

    fun back(activity: Activity) {
        activity.finish()
    }

}