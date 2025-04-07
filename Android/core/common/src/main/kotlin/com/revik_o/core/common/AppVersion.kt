package com.revik_o.core.common

import kotlin.enums.enumEntries

enum class AppVersion(private val _weight: Int) {
    V2_0_0(2 + 0 + 0);

    fun isSupportedVersion(version: AppVersion): Boolean = version._weight <= _weight

    companion object {
        val LATEST_VERSION = V2_0_0

        fun getAppVersionBySignature(signature: String): AppVersion? {
            for (version in enumEntries<AppVersion>()) {
                if (signature == version.name) {
                    return version
                }
            }

            return null
        }
    }
}