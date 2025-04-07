package com.revik_o.wdt.configs

import com.revik_o.core.common.contexts.ApplicationSettingsContextI
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.services.os.ClipboardServiceI
import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import com.revik_o.infrastructure.common.services.os.ResourceServiceI

class AndroidAPI(
    override val appSettings: ApplicationSettingsContextI = ApplicationSettings("TODO"),
    override val clipboardService: ClipboardServiceI = ClipboardService(),
    override val downloadStorageService: DownloadStorageServiceI = DownloadStorageService(),
    override val resourceService: ResourceServiceI = ResourceService()
) : OSAPIInterface {
}