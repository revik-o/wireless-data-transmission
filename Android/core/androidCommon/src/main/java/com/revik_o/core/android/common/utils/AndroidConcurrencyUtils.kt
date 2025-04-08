package com.revik_o.core.android.common.utils

import android.content.Context
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object AndroidConcurrencyUtils {

    fun <T> awaitFromMainThread(ctx: Context, then: () -> T): T {
        val lock = ReentrantLock()
        val ref = AtomicReference<T>()

        lock.withLock {
            lock.newCondition().let { condition ->
                androidx.core.content.ContextCompat.getMainExecutor(ctx).execute {
                    lock.lock()
                    ref.set(then())
                    condition.signal()
                    lock.unlock()
                }
                condition.await()
            }
        }

        return ref.get()
    }

    fun awaitMainThreadOperation(ctx: Context, operation: (Lock, Condition) -> Unit) {
        val lock = ReentrantLock()

        lock.withLock {
            lock.newCondition().let { condition ->
                androidx.core.content.ContextCompat.getMainExecutor(ctx).execute {
                    operation(lock, condition)
                }
                condition.await()
            }
        }
    }
}