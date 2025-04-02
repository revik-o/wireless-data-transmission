package com.revik_o.core.dto

data class Option<T>(val optionHash: Byte, val optionValue: T) {

    companion object {
        fun <T> getOptionValue(type: Class<T>, hash: Byte, vararg options: Option<*>): T? {
            for (option in options) {
                if (hash == option.optionHash) {
                    return type.cast(option.optionValue)
                }
            }

            return null;
        }
    }
}
