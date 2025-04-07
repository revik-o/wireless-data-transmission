package com.revik_o.infrastructure.common

import com.revik_o.core.common.contexts.ApplicationSettingsContextI
import com.revik_o.infrastructure.common.services.os.ClipboardServiceI
import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import com.revik_o.infrastructure.common.services.os.ResourceServiceI

interface OSAPIInterface {

    val appSettings: ApplicationSettingsContextI
    val clipboardService: ClipboardServiceI
    val downloadStorageService: DownloadStorageServiceI
    val resourceService: ResourceServiceI
}