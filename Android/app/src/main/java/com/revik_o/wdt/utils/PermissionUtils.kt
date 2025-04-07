package com.revik_o.wdt.utils

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import androidx.core.content.ContextCompat.checkSelfPermission

object PermissionUtils {

    fun checkApplicationPermissions(ctx: Context, vararg permissionSignatures: String): Boolean {
        for (permission in permissionSignatures) {
            if (checkSelfPermission(ctx, permission) == PERMISSION_DENIED) {
                return false
            }
        }

        return true
    }
}