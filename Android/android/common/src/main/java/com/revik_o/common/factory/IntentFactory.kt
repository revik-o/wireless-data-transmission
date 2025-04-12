package com.revik_o.common.factory

import android.content.Context
import android.content.Intent
import android.os.Build
import com.revik_o.common.compat.intent.IntentCompat
import com.revik_o.common.compat.intent.LegacyIntentCompat
import com.revik_o.common.compat.intent.ModernIntentCompat

object IntentFactory {

    fun createIntentCompat(intent: Intent): IntentCompat =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ModernIntentCompat(intent) else LegacyIntentCompat(intent)

    fun createIntentCompat(context: Context, type: Class<*>): IntentCompat =
        createIntentCompat(Intent(context, type))
}