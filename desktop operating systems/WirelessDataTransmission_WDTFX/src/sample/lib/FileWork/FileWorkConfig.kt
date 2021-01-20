package sample.lib.FileWork

import java.io.File

val BUFFER_SIZE_FOR_TRANSFER = 1024 * 8

var DIRECTORY_FOR_DOWNLOAD_FILES = File("${System.getProperty("user.home")}/Downloads/WirelessDataTransmission_WDTFX")
    get set