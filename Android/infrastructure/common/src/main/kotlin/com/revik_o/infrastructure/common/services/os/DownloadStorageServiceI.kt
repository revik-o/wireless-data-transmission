package com.revik_o.infrastructure.common.services.os

import com.revik_o.infrastructure.common.dtos.RemoteResourceData
import java.io.OutputStream

interface DownloadStorageServiceI {

    fun isServicePermitted(
        deviceTitle: String,
        resources: Int
    ): DownloadStorageServiceI? = this

    fun createResourceOutputStream(resource: RemoteResourceData): OutputStream?

    companion object {

        fun handlePathValue(path: String): String =
            Regex("(/{2,})|(\\./)|(\\.\\./)").let { regexp ->
                path.replace(regexp, "/").replace(regexp, "/")
            }
    }
}