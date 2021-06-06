package com.WDTComponents.DelegateMethods

interface IOpenDataMethod {

    fun processForClipboard(data: String)

    fun openFile(path: String)

    fun openFolderInFileManager(path: String)

    fun openDownloadFolder()

}