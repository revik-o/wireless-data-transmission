package com.revik_o.impl.service

import android.content.ContextWrapper
import android.net.Uri
import android.provider.MediaStore.MediaColumns.DISPLAY_NAME
import android.provider.MediaStore.MediaColumns.RELATIVE_PATH
import android.provider.MediaStore.MediaColumns.SIZE
import androidx.core.net.toFile
import com.revik_o.infrastructure.common.dtos.ResourceData
import com.revik_o.infrastructure.common.services.os.ResourceServiceI
import java.io.InputStream
import java.io.OutputStream

private const val BUFFER_SIZE_FOR_TRANSFER = 4096

class ResourceService(private val _context: ContextWrapper) : ResourceServiceI<Uri> {

    override fun writeResourceData(resource: Uri, to: OutputStream): Boolean =
        _context.contentResolver.openInputStream(resource).let { from ->
            if (from != null) {
                from.use {
                    var countOfWrite: Int

                    val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)

                    while (from.read(byteArray).also { countOfWrite = it } > 0) {
                        to.write(byteArray, 0, countOfWrite)
                    }

                    to.flush()
                }
                true
            } else {
                false
            }
        }

    override fun readResourceData(
        from: InputStream,
        to: OutputStream,
        expectedLength: Long,
        check: () -> Unit
    ): Boolean {
        var length = 0
        var countOfWrite: Int
        val byteArray = ByteArray(BUFFER_SIZE_FOR_TRANSFER)

        while (from.read(byteArray).also { countOfWrite = it } > 0) {
            length += countOfWrite

            if (length >= expectedLength) {
                to.write(byteArray, 0, countOfWrite)
                break
            }

            to.write(byteArray, 0, countOfWrite)
        }

        to.flush()
        check()
        to.close()
        return false
    }

    override fun getResourcesFromRefs(vararg refs: Uri): Array<ResourceData<Uri>> {
        val resources = ArrayList<ResourceData<Uri>>()

        for (ref in refs) {
            when (ref.scheme?.lowercase()) {
                "file" -> ref.toFile().let { file ->
                    resources.add(ResourceData(file.name, file.length(), ref))
                }

                else -> _context.contentResolver.query(
                    ref,
                    arrayOf(DISPLAY_NAME, RELATIVE_PATH, SIZE),
                    null,
                    null,
                    null
                )?.use { cursor ->
                    while (cursor.moveToNext()) {
                        val resourceSize = cursor.getLong(cursor.getColumnIndexOrThrow(SIZE))

                        if (resourceSize > 0) {
                            val relativePath = cursor.getColumnIndex(RELATIVE_PATH).let { index ->
                                if (index == -1) {
                                    ""
                                } else {
                                    cursor.getString(index)
                                }
                            }
                            val resourceName =
                                cursor.getString(cursor.getColumnIndexOrThrow(DISPLAY_NAME))

                            resources.add(
                                ResourceData("$relativePath$resourceName", resourceSize, ref)
                            )
                        }
                    }
                }
            }
        }

        return resources.toTypedArray()
    }
}