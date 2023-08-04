package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.ParametersActivity

import android.app.Activity
import android.content.Intent
import android.view.View
import ua.edu.onaft.wirelessdatatransmission_wdt.HistoryActivity

class HistoryButtonOnClickListener(activity: Activity): View.OnClickListener {

    val activity: Activity = activity

    override fun onClick(v: View?) {
        activity.startActivity(Intent(activity, HistoryActivity::class.java))
    }

}