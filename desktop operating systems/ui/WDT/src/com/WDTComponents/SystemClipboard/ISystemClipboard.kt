package com.WDTComponents.SystemClipboard

interface ISystemClipboard {

    fun setContent(string: String)
    fun getTypeContent(): String
    fun getTextContent(): String
    fun getContentFile(): ClipboardFile

}