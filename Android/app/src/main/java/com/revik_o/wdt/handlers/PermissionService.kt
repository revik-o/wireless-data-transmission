package com.revik_o.wdt.handlers

import android.app.Activity
import android.os.Build
import com.revik_o.wdt.dtos.ApplicationPermission

private const val APPLICATION_REQUEST_CODE = 3123421

class PermissionService(private val _activity: Activity) {

    fun askEnablePermission(than: () -> Unit = {}, vararg permissions: ApplicationPermission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _activity.onRequestPermissionsResult(
                APPLICATION_REQUEST_CODE,
                ApplicationPermission.extractPermissionSignatures(*permissions),
                ApplicationPermission.extractExpectedGrantSignatures(*permissions)
            )
        }
    }
}