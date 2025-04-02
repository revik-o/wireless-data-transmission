package com.revik_o.core.service

import com.revik_o.core.dto.EncryptionDto

interface EncryptionServiceI {

    fun encrypt(data: String): EncryptionDto

    fun decrypt(encryptedData: EncryptionDto): String
}