package com.revik_o.test

import com.revik_o.core.data.AppDatabase
import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.test.repos.DeviceTestRepo
import com.revik_o.test.repos.HistoryTestRepo
import com.revik_o.test.repos.TrustedDeviceTestRepo
import com.revik_o.test.repos.UntrustedDeviceTestRepo
import com.revik_o.test.service.os.TestClipboardService
import com.revik_o.test.service.os.TestDownloadStorageService
import com.revik_o.test.service.os.TestResourceService
import java.io.File

class TestOSAPI(
    override var appSettings: ApplicationTestSettings = ApplicationTestSettings(),
    override var clipboardService: TestClipboardService = TestClipboardService(),
    override var downloadStorageService: TestDownloadStorageService = TestDownloadStorageService(),
    override var resourceService: TestResourceService = TestResourceService(),
    override val appDatabase: AppDatabase = AppDatabase(
        DeviceTestRepo(),
        HistoryTestRepo(),
        TrustedDeviceTestRepo(),
        UntrustedDeviceTestRepo()
    )
) : OSAPIInterface<File>