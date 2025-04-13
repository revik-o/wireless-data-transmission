package com.revik_o.core.common.utils

import com.revik_o.core.common.utils.ConcurrencyUtils.runConcurrentIOOperation
import com.revik_o.core.common.utils.ConcurrencyUtils.runConcurrentOperation
import com.revik_o.core.common.utils.ConcurrencyUtils.runFetchOperation
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference

private const val RESULT_MESSAGE = "Concurrent test"

class ConcurrencyUtilsTest {

    @Test
    fun runConcurrentIOOperationTest() = runTest {
        val message = AtomicReference("")
        val job = runConcurrentIOOperation {
            delay(100)
            message.set("${message.get()} test")
        }

        message.set("${message.get()}Concurrent")

        assertEquals("Concurrent", message.get())

        job.join()

        assertEquals(RESULT_MESSAGE, message.get())
    }

    @Test
    fun runFetchOperationTest() = runTest {

        val asyncCtx = runFetchOperation {
            delay(100)
            return@runFetchOperation "$RESULT_MESSAGE async"
        }

        assertEquals("$RESULT_MESSAGE async", asyncCtx.fetch())
    }

    @Test
    fun runConcurrentOperationTest() {
        val message = AtomicReference("")
        val thread = runConcurrentOperation {
            Thread.sleep(100)
            message.set(RESULT_MESSAGE)
        }

        assertEquals("", message.get())
        thread.join()
        assertEquals(RESULT_MESSAGE, message.get())
    }
}