package ua.edu.onaft.wirelessdatatransmission_wdt

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.WDTComponents.ArgClass.FileInfo
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.ScreenDimension
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import ua.edu.onaft.wirelessdatatransmission_wdt.Configuration.DefaultApplicationConfig
import ua.edu.onaft.wirelessdatatransmission_wdt.Configuration.SystemClipboardConfiguration
import java.io.File
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SplashScreen : AppCompatActivity() {

    val lock = ReentrantLock()
    val condition: Condition = lock.newCondition()
    var checkIntentAction = false

    @RequiresApi(Build.VERSION_CODES.N)
    private fun config() {
        /**
         * Update activity
         */
        SessionState.context = this
        /**
         * Check Permissions
         */
        Method.checkPermissions(this)
        /**
         * External Storage
         */
        Constant.mainExternalStorageDirectory = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R)
            Environment.getExternalStorageDirectory()
        else
            applicationContext.getExternalFilesDir("/")
        /**
         * adapting activity
         */
        ScreenDimension(this).also {
            Constant.usualSpace = (it.height / 11.55).toInt()
            Constant.specialSpace = (Constant.usualSpace * 2)
        }
        /**
         * Start Service
         */
        DefaultApplicationConfig()
        Intent(this, BGService::class.java).also { startService(it) }
        /**
         * Intent actions
         */
        if (intent.action.equals(Intent.ACTION_SEND_MULTIPLE)) {
            intent.getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM)?.forEach { processUriFile(it) }
            checkChosenFiles()
        } else if (intent.action.equals(Intent.ACTION_SEND)) {
            SessionState.sendType = 2
            intent.getStringExtra(Intent.EXTRA_TEXT)?.also {
                checkIntentAction = true
                SystemClipboardConfiguration().setContent(it)
                startActivity(Intent(this, ListOfDevicesActivity::class.java))
            }
            intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)?.also {
                processUriFile(it)
                checkChosenFiles()
            }
        }
        lock.withLock { condition.signal() }
    }

    private fun processUriFile(uri: Uri) {
        val path: String = Uri.decode(uri.path)
        if (path.contains("file://")) {
            val file = File(path.substring(path.indexOf("://") + 3))
            if (file.exists()) SessionState.chosenFiles.add(file)
        } else {
            SessionState.sendType = 3
            val file: DocumentFile? = DocumentFile.fromSingleUri(this, uri)
            if (file != null && file.isFile)
                file.name?.let { fileName -> contentResolver.openInputStream(uri)?.let { inputStream ->
                    SessionState.fileInfoArrayList.add(FileInfo(fileName, "/enc/$fileName", file.length(), inputStream))
                }}
        }
    }

    private fun checkChosenFiles() {
        when (true) {
            SessionState.chosenFiles.isNotEmpty() -> {
                startListOfDevicesActivity()
            }
            SessionState.fileInfoArrayList.isNotEmpty() -> {
                startListOfDevicesActivity()
            }
            else -> {
                Toast.makeText(this, "Don't have file", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startListOfDevicesActivity() {
        checkIntentAction = true
        if (SessionState.sendType != 3)
            SessionState.sendType = 0
        startActivity(Intent(this, ListOfDevicesActivity::class.java))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Thread {
            lock.withLock {
                condition.await()
                if (!checkIntentAction) {
                    runOnUiThread {
                        startActivity(Intent(this, WelcomeActivity::class.java))
                    }
                }
            }
        }.start()
        config()
    }

}