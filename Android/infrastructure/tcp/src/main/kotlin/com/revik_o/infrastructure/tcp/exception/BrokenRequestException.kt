package com.revik_o.infrastructure.tcp.exception

class BrokenRequestException : RuntimeException {

    constructor() : super()

    constructor(msg: String) : super(msg)
}