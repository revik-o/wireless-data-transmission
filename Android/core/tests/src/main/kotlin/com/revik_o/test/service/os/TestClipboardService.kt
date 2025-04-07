package com.revik_o.test.service.os

import com.revik_o.infrastructure.common.services.os.ClipboardServiceI

class TestClipboardService(var putData: ((String) -> Boolean)? = null) : ClipboardServiceI {

    @Volatile
    private var _clipboard: String? = null
        @Synchronized set

    override val clipboardTextData: String?
        get() = _clipboard

    override fun putDataFromRemoteClipboard(data: String): Boolean {
        return if (putData != null) {
            putData!!.invoke(data)
        } else {
            _clipboard = data
            true
        }
    }
}