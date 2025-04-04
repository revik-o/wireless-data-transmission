package com.revik_o.core

enum class AppVersion(private val _weight: Int) {
    @Deprecated(message = "v2.0.0 is not backward compatible with v1.0.0")
    V1_0_0(Int.MAX_VALUE),
    V2_0_0(2 + 0 + 0);

    fun isSupportedVersion(version: AppVersion): Boolean =
        version != V1_0_0 && this != V1_0_0 && version._weight <= _weight

    companion object {
        val LATEST_VERSION = V2_0_0

        fun getAppVersionBySignature(signature: String): AppVersion? {
            for (version in AppVersion.entries) {
                if (signature == version.name) {
                    return version
                }
            }

            return null
        }
    }
}