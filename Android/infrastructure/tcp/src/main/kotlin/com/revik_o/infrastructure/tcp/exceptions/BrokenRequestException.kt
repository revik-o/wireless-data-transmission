package com.revik_o.infrastructure.tcp.exceptions

class BrokenRequestException : RuntimeException {

    constructor() : super()

    constructor(msg: String) : super(msg)
}