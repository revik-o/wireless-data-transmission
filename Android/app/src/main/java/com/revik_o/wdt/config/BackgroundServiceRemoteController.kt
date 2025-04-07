package com.revik_o.wdt.config

import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipData.Item
import android.content.ClipDescription
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.revik_o.config.RemoteControllerI
import com.revik_o.wdt.utils.AlertDialogUtils.showAcceptDataFromDevice
import com.revik_o.wdt.utils.PermissionUtils
import java.io.File
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

private const val APPLICATION_CLIPBOARD_DESCRIPTION = "Clipboard value from remote device (WDT app)"

class BackgroundServiceRemoteController(private val _context: Context) : RemoteControllerI {

    override fun onAcceptClipboard(data: String): Boolean {
        val manager = getSystemService(_context, ClipboardManager::class.java)

        if (manager != null) {
            val result = AtomicBoolean(false)

            showAcceptDataFromDevice(_context, yes = {
                manager.setPrimaryClip(
                    ClipData(
                        ClipDescription(
                            APPLICATION_CLIPBOARD_DESCRIPTION,
                            arrayOf(MIMETYPE_TEXT_PLAIN)
                        ),
                        Item(data)
                    )
                )
                result.set(true)
            })

            return result.get()
        }

        return false
    }

    override fun onAcceptResources(filesSize: UInt, folderSize: UInt): Boolean {
        val result = AtomicBoolean(false)

        showAcceptDataFromDevice(_context, yes = { result.set(true) })

        return result.get()
    }

    override fun onSendClipboard(): CharSequence? {
        val manager = getSystemService(_context, ClipboardManager::class.java)

        return when {
            manager != null && manager.hasPrimaryClip() -> {
                if (manager.primaryClip != null) {
                    val primaryClip = manager.primaryClip

                    if (primaryClip != null) {
                        val firstItem = primaryClip.getItemAt(0)

                        if (firstItem != null) {
                            return firstItem.text ?: firstItem.uri.path
                        }
                    }
                }

                return null
            }

            else -> null
        }
    }

    override fun systemCallMkDir(dirPath: String): Boolean {
        if (PermissionUtils.checkApplicationPermissions(
                _context,
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE
            )
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, dirPath)
                    put(MediaStore.Downloads.IS_PENDING, 1)
                    put(
                        MediaStore.Downloads.RELATIVE_PATH,
                        Environment.DIRECTORY_DOWNLOADS + "/" + dirPath
                    )
                }

                val uri = _context.contentResolver.insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    contentValues
                )

                if (uri != null) {
                    contentValues.clear()
                    contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                    _context.contentResolver.update(uri, contentValues, null, null)
                    return true
                }
            } else {
                val downloadsDirectory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val newDirectory = File(downloadsDirectory, dirPath)
                if (!newDirectory.exists()) {
                    return newDirectory.mkdirs()
                }
            }
        }

        return false
    }

    override fun systemCallMkResource(resourcePath: String): File? {
        if (PermissionUtils.checkApplicationPermissions(
                _context,
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE
            )
        ) {
            val downloadsDirectory = _context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                ?: return null

            val newFile = File(downloadsDirectory, resourcePath)
            return try {
                if (newFile.createNewFile()) {
                    newFile
                } else {
                    null
                }
            } catch (_: IOException) {
                null
            }
        }

        return null
    }

    @SuppressLint("MissingPermission")
    override fun checkProgress(resourceName: String, progress: UShort) =
        with(NotificationManagerCompat.from(_context)) {
            if (PermissionUtils.checkApplicationPermissions(_context, POST_NOTIFICATIONS)) {
                if (progress >= 100u) {
                    cancel(2123123)
                } else {
                    notify(
                        2123123, NotificationCompat.Builder(_context, "download_channel")
                            .setSmallIcon(android.R.drawable.stat_sys_download) // Replace with your app's icon
                            .setContentTitle("test TODO")
                            .setPriority(NotificationCompat.PRIORITY_LOW) // Or PRIORITY_HIGH
                            .setOngoing(true) // Make the notification non-dismissable by swiping
                            .setProgress(100, progress.toInt(), false).build()
                    )
                }
            }
        }
}