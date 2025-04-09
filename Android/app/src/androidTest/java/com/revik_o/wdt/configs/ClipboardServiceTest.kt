package com.revik_o.wdt.configs

import android.app.AlertDialog
import android.content.DialogInterface.OnClickListener
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.revik_o.impl.service.ClipboardService
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import java.util.concurrent.atomic.AtomicReference
import org.mockito.Mockito.`when` as mockWhen

@RunWith(AndroidJUnit4::class)
class ClipboardServiceTest {

    @Test
    fun putDataFromRemoteClipboardTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val positiveFunc = AtomicReference<OnClickListener>()
        val testData = "ClipboardServiceTest data"
        val mockAlertDialogBuilder = mock(AlertDialog.Builder::class.java)

        mockWhen(
            mockAlertDialogBuilder.setPositiveButton(
                anyString(),
                any()
            )
        ).thenAnswer { invocation ->
            positiveFunc.set(invocation.arguments[1] as OnClickListener)
            mockAlertDialogBuilder
        }
        mockWhen(mockAlertDialogBuilder.show()).thenAnswer {
            positiveFunc.get().onClick(null, 0)
        }
        mockWhen(AlertDialog.Builder(appContext)).thenReturn(mockAlertDialogBuilder)


        val service = ClipboardService(appContext)
        assertTrue(service.putDataFromRemoteClipboard(testData))
    }
}