package com.revik_o.core.context

import com.revik_o.core.entity.HistoryEntity.Companion.ResourceType

interface ClipboardCommunicationContextI: CommunicationContextI {

    override val resourceType: ResourceType
        get() = ResourceType.CLIPBOARD_DATA
    val data: String
}