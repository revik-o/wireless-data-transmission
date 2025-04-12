package com.revik_o.impl.service

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Build
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.MediaColumns.DISPLAY_NAME
import android.provider.MediaStore.MediaColumns.IS_PENDING
import android.provider.MediaStore.MediaColumns.RELATIVE_PATH
import androidx.annotation.RequiresApi
import com.revik_o.common.utils.PermissionUtils.checkApplicationPermissions
import com.revik_o.infrastructure.common.dtos.RemoteResourceData
import com.revik_o.infrastructure.common.services.os.DownloadStorageServiceI
import java.io.File
import java.io.FileOutputStream
import java.io.Flushable
import java.io.IOException
import java.io.OutputStream

private const val APPLICATION_DIR = "WDT"

class DownloadStorageService(private val _context: ContextWrapper) : DownloadStorageServiceI {

    private val _mkDirImpl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        ContentResolverMkDir(_context)
    } else {
        LegacyMkDir(_context)
    }

    private val _resourceOutputStreamFactory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        ContentResolverResourceOutputStreamFactory(_context)
    } else {
        LegacyResourceOutputStreamFactoryI(_context)
    }

    override fun isServicePermitted(
        deviceTitle: String,
        resources: Int
    ): DownloadStorageServiceI? = if (checkApplicationPermissions(
            _context,
            WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE
        )
    ) {
        this
    } else {
        null
    }

    override fun createResourceOutputStream(resource: RemoteResourceData): OutputStream? {
        if (!_mkDirImpl.mkDir(resource.dirSequence)) {
            return null
        }

        return _resourceOutputStreamFactory.createOutputStream(
            "${resource.dirSequence}${resource.fileName}",
            resource.size
        )
    }
}

private fun interface MkDirI {
    fun mkDir(dirPath: String): Boolean
}

@RequiresApi(Build.VERSION_CODES.Q)
private class ContentResolverMkDir(private val _context: ContextWrapper) : MkDirI {

    override fun mkDir(dirPath: String): Boolean {
        val contentValues = ContentValues().apply {
            put(DISPLAY_NAME, dirPath)
            put(IS_PENDING, 1)
            put(RELATIVE_PATH, "${DIRECTORY_DOWNLOADS}/${APPLICATION_DIR}/$dirPath")
        }

        val uri = _context.contentResolver.insert(EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            contentValues.clear()
            contentValues.put(IS_PENDING, 0)
            _context.contentResolver.update(uri, contentValues, null, null)
            return true
        }

        return false
    }
}

private class LegacyMkDir(private val _context: Context) : MkDirI {
    override fun mkDir(dirPath: String): Boolean {
        if (checkApplicationPermissions(_context, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)) {
            val downloadsDirectory = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
            val newDirectory = File(downloadsDirectory, "${APPLICATION_DIR}/$dirPath")
            return newDirectory.exists() || newDirectory.mkdirs()
        }

        return false
    }
}

private class DownloadStorageOutputSteam(
    private val _length: Long,
    private val _outputStream: OutputStream,
    private val _uri: Uri? = null,
    private val _context: ContextWrapper? = null,
) : OutputStream(),
    Flushable by _outputStream {

    private var _sentLength = 0

    override fun write(b: Int) {
        _outputStream.write(b)
    }

    override fun write(b: ByteArray?, off: Int, len: Int) {
        _outputStream.write(b, off, len)
        _sentLength += len

        if (_sentLength >= _length) {
//                TODO()
        }
    }

    override fun write(b: ByteArray?) {
        _outputStream.write(b)
    }

    override fun close() {
        if (_uri != null && _context != null) {
            val contentValues = ContentValues().apply {
                put(IS_PENDING, 1)
            }
            contentValues.put(IS_PENDING, 0)
            _context.contentResolver.update(_uri, contentValues, null, null)
        }
        _outputStream.close()
    }
}

private fun interface ResourceOutputStreamFactoryI {
    fun createOutputStream(resourcePath: String, length: Long): OutputStream?
}

@RequiresApi(Build.VERSION_CODES.Q)
private class ContentResolverResourceOutputStreamFactory(private val _context: ContextWrapper) :
    ResourceOutputStreamFactoryI {

    override fun createOutputStream(resourcePath: String, length: Long): OutputStream? {
        val contentValues = ContentValues().apply {
            val displayName = resourcePath.substring(resourcePath.lastIndexOf("/") + 1)
            val relativePath = resourcePath.substring(0, resourcePath.lastIndexOf("/")).let {
                if (it.isBlank())
                    "${DIRECTORY_DOWNLOADS}/${APPLICATION_DIR}"
                else
                    "${DIRECTORY_DOWNLOADS}/${APPLICATION_DIR}/$it"
            }

            put(DISPLAY_NAME, displayName)
            put(RELATIVE_PATH, relativePath)
            put(IS_PENDING, 1)
        }

        val uri = _context.contentResolver.insert(EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            val outputStream = _context.contentResolver.openOutputStream(uri)

            if (outputStream != null) {
                return DownloadStorageOutputSteam(length, outputStream, uri, _context)
            }
        }

        return null
    }
}

private class LegacyResourceOutputStreamFactoryI(private val _context: ContextWrapper) :
    ResourceOutputStreamFactoryI {
    override fun createOutputStream(resourcePath: String, length: Long): OutputStream? {
        if (checkApplicationPermissions(_context, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)) {
            val downloadsDirectory = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)
                ?: return null

            val newFile = File(downloadsDirectory, "$APPLICATION_DIR/$resourcePath")
            return try {
                if (newFile.createNewFile()) {
                    DownloadStorageOutputSteam(length, FileOutputStream(newFile))
                } else {
                    null
                }
            } catch (_: IOException) {
                null
            }
        }

        return null
    }
}