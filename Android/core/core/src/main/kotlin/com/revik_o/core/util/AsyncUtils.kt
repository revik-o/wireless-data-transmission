package com.revik_o.core.util

object AsyncUtils {

    fun runAsync(logic: Runnable) {
        Thread(logic).start()
    }
}