package com.WDTlib.Other

fun isNumeric(string: String): Boolean {
    return try {
        string.toLong()
        true
    }
    catch (E: Exception) {
        false
    }
}