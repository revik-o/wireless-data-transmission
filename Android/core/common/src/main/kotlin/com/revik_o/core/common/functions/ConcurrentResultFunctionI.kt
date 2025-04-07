package com.revik_o.core.common.functions

fun interface ConcurrentResultFunctionI<out T> {

    suspend fun invoke(): T
}