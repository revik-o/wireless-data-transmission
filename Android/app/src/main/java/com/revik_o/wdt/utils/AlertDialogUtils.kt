package com.revik_o.wdt.utils

import android.app.AlertDialog
import android.content.Context
import com.revik_o.core.android.common.utils.AndroidConcurrencyUtils.awaitMainThreadOperation

object AlertDialogUtils {

    /**
     * @showAcceptDataFromDevice is a blocking dialog method
     */
    fun showAcceptDataFromDeviceAlert(ctx: Context, yes: () -> Unit, no: () -> Unit = {}) {
        awaitMainThreadOperation(ctx) { lock, condition ->
            val dialog = AlertDialog.Builder(ctx).setMessage("Ok?")
                .setPositiveButton("Ok") { _, _ ->
                    lock.lock()
                    yes()
                    condition.signal()
                    lock.unlock()
                }
                .setNegativeButton("Cancel") { _, _ -> // FIXME i18n
                    lock.lock()
                    no()
                    condition.signal()
                    lock.unlock()
                }
                .create() ?: throw IllegalStateException("Activity cannot be null")
            dialog.show()
        }
    }
}