package com.revik_o.core.common.contexts

import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicReference

class AsyncContext<out T>(private val _jobRef: Job, private val _valRef: AtomicReference<out T>) {

    fun fetch(): T = runBlocking { _jobRef.join(); _valRef.get(); }
}