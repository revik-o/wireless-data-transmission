package com.revik_o.wdt.service

import android.app.Activity
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import com.revik_o.wdt.dto.ApplicationPermission
import com.revik_o.wdt.dto.ApplicationPermission.Companion.extractExpectedGrantSignatures
import com.revik_o.wdt.dto.ApplicationPermission.Companion.extractPermissionSignatures

private const val APPLICATION_REQUEST_CODE = 3123421

class PermissionService(private val _activity: Activity) {

    fun askEnablePermission(than: () -> Unit = {}, vararg permissions: ApplicationPermission) {
        if (SDK_INT >= M) {
            _activity.onRequestPermissionsResult(
                APPLICATION_REQUEST_CODE,
                extractPermissionSignatures(*permissions),
                extractExpectedGrantSignatures(*permissions)
            )
        }
    }
}