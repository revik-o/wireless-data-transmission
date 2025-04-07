package com.revik_o.test

import com.revik_o.infrastructure.common.OSAPIInterface
import com.revik_o.test.service.os.TestClipboardService
import com.revik_o.test.service.os.TestDownloadStorageService
import com.revik_o.test.service.os.TestResourceService

class TestOSAPI(
    override var appSettings: ApplicationTestSettings = ApplicationTestSettings(),
    override var clipboardService: TestClipboardService = TestClipboardService(),
    override var downloadStorageService: TestDownloadStorageService = TestDownloadStorageService(),
    override var resourceService: TestResourceService = TestResourceService()
) : OSAPIInterface {
}