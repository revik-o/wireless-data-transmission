package com.revik_o.common.service.ui

interface AlertDialogServiceI {

    fun showAcceptDataAlert(yes: () -> Unit, no: () -> Unit = {})
}