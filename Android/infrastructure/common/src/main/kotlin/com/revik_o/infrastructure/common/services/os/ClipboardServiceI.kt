package com.revik_o.infrastructure.common.services.os

interface ClipboardServiceI {

    val clipboardTextData: String?

    fun isServicePermitted(deviceTitle: String): ClipboardServiceI? = this

    fun putDataFromRemoteClipboard(data: String): Boolean
}