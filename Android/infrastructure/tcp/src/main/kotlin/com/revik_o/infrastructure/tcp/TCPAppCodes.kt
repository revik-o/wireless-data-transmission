package com.revik_o.infrastructure.tcp

object TCPAppCodes {

    const val OP_DONE_STATUS = 1
    const val OK_STATUS = 0
    const val UNSUPPORTED_VERSION = -1
    const val UNSUPPORTED_OS = -2
    const val UNSUPPORTED_REQUEST = -3
    const val DECLINED_STATUS = -4
    const val BROKEN_REQUEST = -5
    const val EMPTY_RESOURCE = -6
}