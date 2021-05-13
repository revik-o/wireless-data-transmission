package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.Manifest.permission.*
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.view.ViewGroup
import android.widget.Space
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment.DirectoryOrFileOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel

object Method {

    private fun checkSelfPermission(permissions: Array<String>, checkedPermissions: BooleanArray) {
        for ((i, permission) in permissions.withIndex())
            checkedPermissions[i] = ContextCompat.checkSelfPermission(SessionState.context.applicationContext, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkBooleanArrayOnTrue(booleanArray: BooleanArray): Boolean {
        for (boolean in booleanArray) if (!boolean) return false; return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkPermissions(activity: Activity): Boolean {
        val permissions: Array<String> = arrayOf(
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
            INTERNET,
            SYSTEM_ALERT_WINDOW,
            FOREGROUND_SERVICE
        )
        val checkedPermissions = BooleanArray(permissions.size)
        checkSelfPermission(permissions, checkedPermissions)
        if (!checkBooleanArrayOnTrue(checkedPermissions))
            ActivityCompat.requestPermissions(activity, permissions, 1)
        checkSelfPermission(permissions, checkedPermissions)
        return checkBooleanArrayOnTrue(checkedPermissions)
    }

    fun addInLinearLayoutNewSpace(activity: Activity, linearLayout: ViewGroup, spaceHeight: Int) {
        val space = Space(activity)
        space.layoutParams = ViewGroup.LayoutParams(0, spaceHeight)
        linearLayout.addView(space)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun fillMainLinearLayoutForFileViewerFragment(
        activity: Activity,
//            screenDimension: ScreenDimension,
        fileViewerViewModel: FileViewerViewModel,
        linearLayout: ViewGroup
    ) {
        if (Constant.mainExternalStorageDirectory != null) {
            val mainDirectory = CustomFrameLayout(
                activity,
//                    screenDimension,
                Constant.mainExternalStorageDirectory!!,
                false,
                Build.MODEL
            )
            mainDirectory.frameLayout.setOnClickListener(DirectoryOrFileOnClickListener(activity, fileViewerViewModel, mainDirectory))
            linearLayout.removeAllViews()
            addInLinearLayoutNewSpace(activity, linearLayout, Constant.specialSpace)
            linearLayout.addView(mainDirectory.frameLayout)
        } else {
            if (!checkPermissions(activity)) {
                Toast.makeText(activity, "Problem With Permissions".intern(), Toast.LENGTH_LONG).show()
                activity.finish()
            }
        }
    }

    fun cleanArrayOfFiles() {
        if (SessionState.chosenFiles.size != 0)
            SessionState.chosenFiles.clear()
    }

}