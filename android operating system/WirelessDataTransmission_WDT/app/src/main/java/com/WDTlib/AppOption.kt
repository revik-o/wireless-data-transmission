package com.WDTlib

import java.io.File
import java.net.InetAddress

/**
 *
 */
object AppOption {
    object Option {

        val SOCKET_PORT = 4000

        val MAX_COUNT_OF_CONNECT = 100

        var SOCKET_TIMEOUT = 500

        val BUFFER_SIZE_FOR_TRANSFER = 1024 * 8

        var LOCAL_DEVICE_NAME = //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            ""

        val DEVICE_TYPE = "COMPUTER"

        var SERVER_SOCKET_IS_ON = true

        lateinit var DIRECTORY_FOR_DOWNLOAD_FILES: File

    }
}