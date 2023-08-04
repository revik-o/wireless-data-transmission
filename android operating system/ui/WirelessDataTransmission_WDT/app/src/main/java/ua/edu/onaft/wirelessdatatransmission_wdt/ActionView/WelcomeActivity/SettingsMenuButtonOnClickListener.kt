package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity

import android.app.Activity
import android.content.Intent
import android.view.View
import ua.edu.onaft.wirelessdatatransmission_wdt.ParametersActivity

class SettingsMenuButtonOnClickListener(activity: Activity): View.OnClickListener {

    private val activity: Activity = activity

    override fun onClick(v: View?) {
        activity.startActivity(Intent(activity, ParametersActivity::class.java))
    }

}