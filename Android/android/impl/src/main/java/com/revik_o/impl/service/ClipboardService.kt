package com.revik_o.impl.service

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat
import com.revik_o.common.service.ui.AlertDialogServiceI
import com.revik_o.common.utils.AndroidConcurrencyUtils
import com.revik_o.impl.service.ui.NativeAlertDialogService
import com.revik_o.infrastructure.common.services.os.ClipboardServiceI
import java.util.concurrent.atomic.AtomicBoolean

private const val APPLICATION_CLIPBOARD_DESCRIPTION = "WDT: remote device clipboard data"

class ClipboardService(
    private val _context: Context,
    private val _alertDialogService: AlertDialogServiceI = NativeAlertDialogService(_context)
) : ClipboardServiceI {

    override val clipboardTextData: CharSequence?
        get() {
            val manager = ContextCompat.getSystemService(_context, ClipboardManager::class.java)

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
        val manager = AndroidConcurrencyUtils.awaitFromMainThread(_context) { await ->
            await {
                ContextCompat.getSystemService(_context, ClipboardManager::class.java)
            }
        }

        if (manager != null) {
            val result = AtomicBoolean(false)

            _alertDialogService.showAcceptDataAlert(yes = {
                manager.setPrimaryClip(
                    ClipData(
                        ClipDescription(
                            APPLICATION_CLIPBOARD_DESCRIPTION,
                            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                        ),
                        ClipData.Item(data)
                    )
                )
                result.set(true)
            })

            return result.get()
        }

        return false
    }
}