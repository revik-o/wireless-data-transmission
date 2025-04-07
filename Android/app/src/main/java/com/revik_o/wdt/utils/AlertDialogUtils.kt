package com.revik_o.wdt.utils

import android.app.AlertDialog
import android.content.Context
import java.util.concurrent.locks.ReentrantLock

object AlertDialogUtils {

    /**
     * @showAcceptDataFromDevice is a blocking dialog method
     */
    fun showAcceptDataFromDevice(ctx: Context, yes: () -> Unit, no: () -> Unit = {}) {
        val locker = ReentrantLock()
        val dialog = AlertDialog.Builder(ctx).setMessage("Ok?")
            .setPositiveButton("Ok") { _, _ ->
                yes()
                locker.unlock()
            }
            .setNegativeButton("Cancel") { _, _ ->
                no()
                locker.unlock()
            }
            .create() ?: throw IllegalStateException("Activity cannot be null")
        locker.lock()
        dialog.show()
        locker.lock()
        locker.unlock()
    }
}