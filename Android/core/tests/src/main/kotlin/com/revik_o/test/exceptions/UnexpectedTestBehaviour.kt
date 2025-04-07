package com.revik_o.test.exceptions

class UnexpectedTestBehaviour : Exception {
    constructor(msg: String) : super(msg)
    constructor() : super()
}