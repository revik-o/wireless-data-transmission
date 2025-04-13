package com.revik_o.infrastructure.common.dtos

import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI.Companion.handlePathValue

class RemoteResourceData(path: String, val size: Long) {

    val dirSequence: String
    val fileName: String

    init {
        val handledPath = handlePathValue(path)
        this.dirSequence = handledPath.substring(0, handledPath.lastIndexOf('/') + 1)
        this.fileName = handledPath.substring(handledPath.lastIndexOf('/') + 1)
    }
}
