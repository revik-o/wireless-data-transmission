package com.revik_o.tests

import com.revik_o.config.RemoteControllerI
import com.revik_o.tests.exception.UnexpectedTestBehaviour
import com.revik_o.tests.utils.LogUtils
import java.io.File

data class MockRemoteController(
    val outputDirPath: String = "./src/test/resources/.downloads",
    var onAcceptClipboardFunc: (String) -> Boolean = { false },
    var onAcceptResourcesFunc: (UInt, UInt) -> Boolean = { _, _ -> false },
    var onSendClipboardFunc: () -> String? = { null },
    var checkProgressFunc: (String, UShort) -> Unit = { _, _ -> },
) : RemoteControllerI {

    init {
        File(outputDirPath).deleteRecursively()
        File(outputDirPath).mkdirs()
    }

    override fun onAcceptClipboard(data: String) = onAcceptClipboardFunc(data)

    override fun onAcceptResources(filesSize: UInt, folderSize: UInt): Boolean =
        onAcceptResourcesFunc(filesSize, folderSize)

    override fun onSendClipboard(): String? = onSendClipboardFunc()

    override fun systemCallMkDir(dirPath: String): Boolean {
        if (File("$outputDirPath/$dirPath").mkdirs()) {
            LogUtils.debug("created $outputDirPath/$dirPath")
            return true
        }

        return false
    }

    override fun systemCallMkResource(resourcePath: String): File {
        val ptr = File("$outputDirPath/$resourcePath")

        if (!ptr.exists() && !ptr.createNewFile()) {
            throw UnexpectedTestBehaviour("Cannot create new file")
        }

        LogUtils.debug("created $outputDirPath/$resourcePath")

        return ptr
    }

    override fun checkProgress(resourceName: String, progress: UShort) =
        checkProgressFunc(resourceName, progress)
}
