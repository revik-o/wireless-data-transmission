package com.revik_o.core.context

import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType
import java.io.File

interface FileCommunicationContextI : CommunicationContextI {

    override val resourceType: ResourceType
        get() = ResourceType.FILE_OR_FOLDER
    val data: Array<out File>
}