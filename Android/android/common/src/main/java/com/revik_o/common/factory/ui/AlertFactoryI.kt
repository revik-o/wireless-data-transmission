package com.revik_o.common.factory.ui

import android.app.AlertDialog

interface AlertFactoryI {

    fun confirmationAlert(
        title: String,
        positiveButtonName: String = "Ok",
        negativeButtonName: String = "No",
        yes: () -> Unit,
        no: () -> Unit = {}
    ): AlertDialog
}