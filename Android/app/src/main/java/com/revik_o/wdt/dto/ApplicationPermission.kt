package com.revik_o.wdt.dto

import android.content.pm.PackageManager

data class ApplicationPermission(val signature: String, val expectedGrantSignature: Int) {

    companion object {

        fun extractPermissionSignatures(vararg ptr: ApplicationPermission): Array<String> =
            ptr.map { it.signature }.toTypedArray()

        fun extractExpectedGrantSignatures(vararg ptr: ApplicationPermission): IntArray =
            ptr.map { it.expectedGrantSignature }.toIntArray()

        fun createPermissionGrantedSig(permissionSignature: String): ApplicationPermission =
            ApplicationPermission(permissionSignature, PackageManager.PERMISSION_GRANTED)
    }
}
