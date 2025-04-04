package com.revik_o.infrastructure.tcp.exception

open class UnsupportedException : RuntimeException {

    constructor() : super()

    constructor(msg: String) : super(msg)
}