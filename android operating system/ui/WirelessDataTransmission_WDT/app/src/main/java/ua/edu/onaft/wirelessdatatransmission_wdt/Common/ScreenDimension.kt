package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics

class ScreenDimension(activity: Activity) {

    var width: Int = 0
        private set
    var height: Int = 0
        private set

    init {
        getDimension(activity)
    }

    fun getDimension(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            val insets: Insets = activity.windowManager.currentWindowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            width = activity.windowManager.currentWindowMetrics.bounds.width()
            height = activity.windowManager.currentWindowMetrics.bounds.height()
        }
        else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            width = displayMetrics.widthPixels
            height = displayMetrics.heightPixels
        }
    }

}