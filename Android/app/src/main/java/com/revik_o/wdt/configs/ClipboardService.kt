package com.revik_o.wdt.configs

import com.revik_o.infrastructure.common.services.os.ClipboardServiceI

class ClipboardService(override val clipboardTextData: String? = null) : ClipboardServiceI {
    override fun putDataFromRemoteClipboard(data: String): Boolean {
        TODO("Not yet implemented")
    }
}