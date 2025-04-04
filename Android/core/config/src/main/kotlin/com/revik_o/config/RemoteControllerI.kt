package com.revik_o.config

import java.io.File

interface RemoteControllerI {

    fun onAcceptClipboard(data: String): Boolean
    fun onAcceptResources(filesSize: UInt, folderSize: UInt): Boolean
    fun onSendClipboard(): String?
    fun systemCallMkDir(dirPath: String): Boolean
    fun systemCallMkResource(resourcePath: String, resourceName: String): File
    fun checkProgress(resourceName: String, progress: UShort)
}