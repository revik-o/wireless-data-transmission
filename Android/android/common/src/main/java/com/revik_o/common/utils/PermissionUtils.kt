package com.revik_o.common.utils

import android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.S_V2
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale

object PermissionUtils {

    private fun filterPermissionForSDK(vararg permissions: String): Array<String> {
        val result = ArrayList<String>(permissions.size)

        for (permission in permissions) {
            if (permission == MANAGE_EXTERNAL_STORAGE) {
                result.add(if (SDK_INT > S_V2) permission else continue)
            }

            if (permission == READ_EXTERNAL_STORAGE || permission == WRITE_EXTERNAL_STORAGE) {
                result.add(if (SDK_INT <= S_V2) permission else continue)
            }

            result.add(permission)
        }

        return result.toTypedArray()
    }

    fun checkApplicationPermissions(ctx: Context, vararg permissions: String): Boolean {
        for (permission in filterPermissionForSDK(*permissions)) {
            if (checkSelfPermission(ctx, permission) == PERMISSION_DENIED) {
                return false
            }
        }

        return true
    }

    fun shouldRequestPermission(activity: Activity, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (shouldShowRequestPermissionRationale(activity, permission)) {
                return true
            }
        }

        return false
    }

    private fun handlePermission(
        isGranted: Boolean,
        then: () -> Unit,
        onPermissionDenied: () -> Unit
    ) = when {
        isGranted -> then()
        else -> onPermissionDenied()
    }


    fun requestPermission(
        activity: ComponentActivity,
        then: () -> Unit = {},
        onPermissionDenied: () -> Unit = {},
        permissions: Array<String>
    ) {
        if (permissions.size == 1) {
            activity.registerForActivityResult(RequestPermission()) { isGranted ->
                handlePermission(isGranted, then, onPermissionDenied)
            }.launch(permissions.first())
        } else if (permissions.size > 1) {
            activity.registerForActivityResult(RequestMultiplePermissions()) { results ->
                var isGranted = true
                val permissionsList = ArrayList<String>(permissions.size)

                for (permission in results) {
                    if (!permission.value) {
                        isGranted = false
                        permissionsList.add(permission.key)
                    }
                }

                handlePermission(isGranted, then, onPermissionDenied)
            }.launch(permissions)
        }
    }

    fun withPermissions(
        then: () -> Unit = {},
        onPermissionDenied: () -> Unit = {},
        activity: ComponentActivity,
        permissions: Array<String>
    ) {
        when {
            checkApplicationPermissions(activity, *permissions) -> then()
            shouldRequestPermission(activity, *permissions) -> requestPermission(
                activity,
                then,
                onPermissionDenied,
                permissions
            )

            else -> requestPermission(activity, then, onPermissionDenied, permissions)
        }
    }
}