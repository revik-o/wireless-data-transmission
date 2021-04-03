package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
import android.Manifest.permission.INTERNET
import android.Manifest.permission.SYSTEM_ALERT_WINDOW
import android.os.Build
import androidx.annotation.RequiresApi

class CommonMethod {

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermissions() {
        val permissions: Array<String> = arrayOf(
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE,
                MANAGE_EXTERNAL_STORAGE,
                INTERNET,
                SYSTEM_ALERT_WINDOW
        )

        val checkedPermissions = BooleanArray(5)

        for ((i, permission) in permissions.withIndex())
            checkedPermissions[i] = ContextCompat.checkSelfPermission(StaticState.activity.applicationContext, permission) == PackageManager.PERMISSION_GRANTED

        for (checkedPermission in checkedPermissions)
            if (!checkedPermission) {
                StaticState.activity.requestPermissions(permissions, 1)
                break
            }
    }

}