package com.revik_o.common.utils

import android.content.Context
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object AndroidConcurrencyUtils {

    fun <T> awaitFromMainThread(ctx: Context, then: ((() -> T) -> Unit) -> Unit): T {
        val lock = ReentrantLock()
        val ref = AtomicReference<T>()

        lock.withLock {
            lock.newCondition().let { condition ->
                androidx.core.content.ContextCompat.getMainExecutor(ctx).execute {
                    then { routine ->
                        lock.lock()
                        ref.set(routine())
                        condition.signal()
                        lock.unlock()
                    }
                }
                condition.await()
            }
        }

        return ref.get()
    }

    fun awaitMainThreadOperation(ctx: Context, then: ((() -> Unit) -> Unit) -> Unit) {
        val lock = ReentrantLock()

        lock.withLock {
            lock.newCondition().let { condition ->
                androidx.core.content.ContextCompat.getMainExecutor(ctx).execute {
                    then { routine ->
                        lock.lock()
                        routine()
                        condition.signal()
                        lock.unlock()
                    }
                }
                condition.await()
            }
        }
    }
}