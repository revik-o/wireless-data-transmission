package com.revik_o.wdt.configs

import android.content.ClipData
import android.content.ClipData.Item
import android.content.ClipDescription
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import com.revik_o.core.android.common.utils.AndroidConcurrencyUtils.awaitFromMainThread
import com.revik_o.infrastructure.common.services.os.ClipboardServiceI
import com.revik_o.wdt.utils.AlertDialogUtils.showAcceptDataFromDeviceAlert
import java.util.concurrent.atomic.AtomicBoolean

private const val APPLICATION_CLIPBOARD_DESCRIPTION = "WDT: remote device clipboard data"

class ClipboardService(private val _context: Context) : ClipboardServiceI {

    override val clipboardTextData: CharSequence?
        get() {
            val manager = getSystemService(_context, ClipboardManager::class.java)

            return when {
                manager != null && manager.hasPrimaryClip() -> {
                    if (manager.primaryClip != null) {
                        val primaryClip = manager.primaryClip

                        if (primaryClip != null) {
                            val firstItem = primaryClip.getItemAt(0)

                            if (firstItem != null) {
                                return firstItem.text ?: firstItem.uri.path
                            }
                        }
                    }

                    return null
                }

                else -> null
            }
        }

    override fun putDataFromRemoteClipboard(data: String): Boolean {
        val manager = awaitFromMainThread(_context) {
            getSystemService(_context, ClipboardManager::class.java)
        }

        if (manager != null) {
            val result = AtomicBoolean(false)

            showAcceptDataFromDeviceAlert(_context, yes = {
                manager.setPrimaryClip(
                    ClipData(
                        ClipDescription(
                            APPLICATION_CLIPBOARD_DESCRIPTION,
                            arrayOf(MIMETYPE_TEXT_PLAIN)
                        ),
                        Item(data)
                    )
                )
                result.set(true)
            })

            result.get()
        }

        return false
    }
}