package com.revik_o.tests

import com.revik_o.config.RemoteControllerI
import com.revik_o.tests.exception.UnexpectedTestBehaviour
import java.io.File

data class MockRemoteController(
    var onAcceptClipboardFunc: (String) -> Boolean = { false },
    var onAcceptResourcesFunc: (UInt, UInt) -> Boolean = { _, _ -> false },
    var onSendClipboardFunc: () -> String? = { null },
    var checkProgressFunc: (String, UShort) -> Unit = { _, _ -> },
) : RemoteControllerI {

    init {
        File("./src/test/resources/.downloads").mkdirs()
    }

    override fun onAcceptClipboard(data: String) = onAcceptClipboardFunc(data)

    override fun onAcceptResources(filesSize: UInt, folderSize: UInt): Boolean =
        onAcceptResourcesFunc(filesSize, folderSize)

    override fun onSendClipboard(): String? = onSendClipboardFunc()

    override fun systemCallMkDir(dirPath: String): Boolean = File(dirPath).mkdirs()

    override fun systemCallMkResource(resourcePath: String, resourceName: String): File {
        val ptr = File("./src/test/resources/.downloads/$resourcePath/$resourceName")

        if (!ptr.exists() && !ptr.createNewFile()) {
            throw UnexpectedTestBehaviour("Cannot create new file")
        }

        return ptr
    }

    override fun checkProgress(resourceName: String, progress: UShort) =
        checkProgressFunc(resourceName, progress)
}
