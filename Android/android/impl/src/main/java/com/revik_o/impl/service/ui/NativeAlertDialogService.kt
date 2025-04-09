package com.revik_o.impl.service.ui

import android.content.Context
import com.revik_o.common.service.ui.AlertDialogServiceI
import com.revik_o.common.utils.AndroidConcurrencyUtils.awaitMainThreadOperation
import com.revik_o.impl.factory.ui.NativeAlertFactory

class NativeAlertDialogService(private val _ctx: Context) : AlertDialogServiceI {

    private val _factory = NativeAlertFactory(_ctx)

    override fun showAcceptDataAlert(yes: () -> Unit, no: () -> Unit) {
        awaitMainThreadOperation(_ctx) { await ->
            _factory.confirmationAlert("Accept NEW data?", yes = {
                await {
                    yes()
                }
            }, no = {
                await {
                    no()
                }
            }).show()
        }
    }
}