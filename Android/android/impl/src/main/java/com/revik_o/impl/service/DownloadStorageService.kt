package com.revik_o.impl.service

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.MediaColumns.DISPLAY_NAME
import android.provider.MediaStore.MediaColumns.IS_PENDING
import android.provider.MediaStore.MediaColumns.RELATIVE_PATH
import com.revik_o.common.utils.PermissionUtils.checkApplicationPermissions
import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI.Companion.handlePathValue
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class DownloadStorageService(private val _context: Context) : DownloadStorageServiceI {

    override fun isServicePermitted(
        deviceTitle: String,
        resources: Int,
        folders: Int
    ): DownloadStorageServiceI? {
        return if (checkApplicationPermissions(
                _context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            this
        } else {
            null
        }
    }

    override fun mkDir(path: String): Boolean {
        if (checkApplicationPermissions(
                _context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            val dirPath = handlePathValue(path)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(DISPLAY_NAME, dirPath)
                    put(IS_PENDING, 1)
                    put(RELATIVE_PATH, "${Environment.DIRECTORY_DOWNLOADS}/$dirPath")
                }

                val uri = _context.contentResolver.insert(EXTERNAL_CONTENT_URI, contentValues)

                if (uri != null) {
                    contentValues.clear()
                    contentValues.put(IS_PENDING, 0)
                    _context.contentResolver.update(uri, contentValues, null, null)
                    return true
                }
            } else {
                val downloadsDirectory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val newDirectory = File(downloadsDirectory, dirPath)
                return newDirectory.exists() || newDirectory.mkdirs()
            }
        }

        return false
    }

    override fun getResourceOutputStream(path: String, length: Long): OutputStream? {
        val resourcePath = handlePathValue(path)

        if (checkApplicationPermissions(
                _context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            val downloadsDirectory = _context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                ?: return null

            val newFile = File(downloadsDirectory, resourcePath)
            return try {
                if (newFile.createNewFile()) {
                    DownloadStorageOutputSteam(newFile, length)
                } else {
                    null
                }
            } catch (_: IOException) {
                null
            }
        }

        return null
    }

    private inner class DownloadStorageOutputSteam(resource: File, private val _length: Long) :
        FileOutputStream(resource) {

        private var _sentLength = 0

        override fun write(b: ByteArray?, off: Int, len: Int) {
            super.write(b, off, len)
            _sentLength += len

            if (_sentLength >= _length) {
                TODO()
            }
        }
    }
}