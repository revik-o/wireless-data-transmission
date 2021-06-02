package com.WDTComponents.DelegateMethods

interface IOpenDataMethod {

    fun processForSendType4(data: String)

    fun openFile(path: String)

    fun openFolderInFileManager(path: String)

    fun openDownloadFolder()

}