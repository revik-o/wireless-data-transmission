package com.revik_o.impl

import android.content.Context
import com.revik_o.core.common.contexts.ApplicationSettingsContextI
import com.revik_o.impl.service.ClipboardService
import com.revik_o.impl.service.DownloadStorageService
import com.revik_o.impl.service.ResourceService
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.infrastructure.common.services.os.ClipboardServiceI
import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import com.revik_o.infrastructure.common.services.os.ResourceServiceI

class AndroidAPI(
    context: Context,
    override val appSettings: ApplicationSettingsContextI = ApplicationSettings("TODO"),
    override val clipboardService: ClipboardServiceI = ClipboardService(context),
    override val downloadStorageService: DownloadStorageServiceI = DownloadStorageService(context),
    override val resourceService: ResourceServiceI = ResourceService()
) : OSAPIInterface {
}