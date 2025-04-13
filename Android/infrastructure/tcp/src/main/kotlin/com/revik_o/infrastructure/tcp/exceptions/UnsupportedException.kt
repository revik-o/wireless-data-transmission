package com.revik_o.infrastructure.tcp.exceptions

open class UnsupportedException : RuntimeException {

    constructor() : super()

    constructor(msg: String) : super(msg)
}