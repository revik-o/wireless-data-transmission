package ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.WelcomeActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout

import ua.edu.onaft.wirelessdatatransmission_wdt.R

class SideMenuButtonClickListener(activity: Activity, drawerLayout: DrawerLayout): View.OnClickListener {

    val activity: Activity = activity
    val drawerLayout: DrawerLayout = drawerLayout

    @SuppressLint("WrongConstant")
    override fun onClick(v: View?) {
        drawerLayout.openDrawer(Gravity.START)
    }

}