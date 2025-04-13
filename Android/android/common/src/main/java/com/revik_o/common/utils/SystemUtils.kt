package com.revik_o.common.utils

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.net.toUri

object SystemUtils {

    fun prepareOpenUrlIntent(url: CharSequence): Intent = Intent(ACTION_VIEW).also { intent ->
        intent.data = url.toString().toUri()
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
    }
}