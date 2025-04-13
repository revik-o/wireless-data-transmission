package com.revik_o.core.common.functions

fun interface ConcurrentFunctionI {

    suspend operator fun invoke()
}