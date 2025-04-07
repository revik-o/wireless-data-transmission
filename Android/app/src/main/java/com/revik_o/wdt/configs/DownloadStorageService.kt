package com.revik_o.wdt.configs

import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import java.io.OutputStream

class DownloadStorageService: DownloadStorageServiceI {
    override fun mkDir(path: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getResourceOutputStream(path: String, length: Long): OutputStream? {
        TODO("Not yet implemented")
    }
}