package com.revik_o.infrastructure.common.services.os

import java.io.OutputStream

interface DownloadStorageServiceI {

    fun isServicePermitted(
        deviceTitle: String,
        resources: Int,
        folders: Int
    ): DownloadStorageServiceI? = this

    fun mkDir(path: String): Boolean

    fun getResourceOutputStream(path: String, length: Long): OutputStream?
}