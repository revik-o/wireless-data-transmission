package com.revik_o.android.test.service

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.revik_o.common.service.ui.AlertDialogServiceI
import com.revik_o.impl.service.ClipboardService
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClipboardServiceTest {

    @Test
    fun putDataFromRemoteClipboard() {
        val clipboardData = "ClipboardServiceTest data"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val mockDialogService = mockk<AlertDialogServiceI>(relaxed = true)
        val testService = ClipboardService(appContext, mockDialogService)
        every { mockDialogService.showAcceptDataAlert(any(), any()) } answers {
            firstArg<() -> Unit>()()
        }
        assertTrue(testService.putDataFromRemoteClipboard(clipboardData))
    }
}