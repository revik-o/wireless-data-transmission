package com.revik_o.impl.factory.ui

import android.app.AlertDialog
import android.content.Context
import com.revik_o.common.factory.ui.AlertFactoryI

class NativeAlertFactory(private val _ctx: Context) : AlertFactoryI {

    override fun confirmationAlert(
        title: String,
        positiveButtonName: String,
        negativeButtonName: String,
        yes: () -> Unit,
        no: () -> Unit
    ): AlertDialog = AlertDialog.Builder(_ctx).setMessage(title)
        .setPositiveButton(positiveButtonName) { _, _ -> yes() }
        .setNeutralButton(negativeButtonName) { _, _ -> no() }
        .create()
}