package com.revik_o.infrastructure.common.services.os

import com.revik_o.infrastructure.common.dtos.ResourceData
import java.io.InputStream
import java.io.OutputStream

interface ResourceServiceI<R> {

    fun writeResourceData(resource: R, to: OutputStream): Boolean

    fun readResourceData(
        from: InputStream,
        to: OutputStream,
        expectedLength: Long,
        check: () -> Unit = {}
    ): Boolean

    fun getResourcesFromRefs(vararg refs: R): Array<ResourceData<R>>
}