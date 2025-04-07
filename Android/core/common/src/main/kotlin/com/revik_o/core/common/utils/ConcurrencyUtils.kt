package com.revik_o.core.common.utils

import com.revik_o.core.common.contexts.AsyncContext
import com.revik_o.core.common.functions.ConcurrentFunctionI
import com.revik_o.core.common.functions.ConcurrentResultFunctionI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference

object ConcurrencyUtils {

    suspend fun <T> concurrencyScope(func: suspend CoroutineScope.() -> T) = coroutineScope(func)

    fun runConcurrentIOOperation(
        scope: CoroutineScope = CoroutineScope(IO),
        then: ConcurrentFunctionI,
    ) = scope.async { then.invoke() }

    fun <T> runFetchOperation(
        scope: CoroutineScope = CoroutineScope(IO),
        then: ConcurrentResultFunctionI<T>,
    ): AsyncContext<T> = AtomicReference<T>().let { ref ->
        AsyncContext(scope.launch { ref.set(then.invoke()) }, ref)
    }

    fun runConcurrentOperation(then: Runnable): Thread = Thread(then).also { it.start() }
}