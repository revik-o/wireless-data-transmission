package com.revik_o.core.common.utils

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

fun ConcurrencyUtils.sleep(ms: Long) = Thread.sleep(ms)

suspend fun ConcurrencyUtils.sleepCoroutine(ms: Long) = coroutineScope { delay(ms) }
