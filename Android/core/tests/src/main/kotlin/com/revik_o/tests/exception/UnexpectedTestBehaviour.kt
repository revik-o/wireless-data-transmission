package com.revik_o.tests.exception

class UnexpectedTestBehaviour : Exception {
    constructor(msg: String) : super(msg)
    constructor() : super()
}