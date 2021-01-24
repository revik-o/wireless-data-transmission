package sample.Platform

import java.io.File

val OS_LANGUAGE: String = System.getProperty("user.language")
    get

val COMMON_DIRECTORY: String = File("common").absolutePath
    get