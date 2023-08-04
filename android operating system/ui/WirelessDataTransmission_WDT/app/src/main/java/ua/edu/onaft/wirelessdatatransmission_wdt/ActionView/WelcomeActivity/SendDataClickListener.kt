package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils

import ua.edu.onaft.wirelessdatatransmission_wdt.FileViewerActivity
import ua.edu.onaft.wirelessdatatransmission_wdt.R

class SendDataClickListener(activity: Activity, viewGroup: ViewGroup): View.OnClickListener {

    val activity: Activity = activity
    val viewGroup: ViewGroup = viewGroup

    override fun onClick(v: View?) {
        viewGroup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.frame_layout_click_animation))
        activity.startActivity(Intent(activity.applicationContext, FileViewerActivity::class.java))
    }

}