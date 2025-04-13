package com.revik_o.wdt

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.INTERNET
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.Intent.ACTION_SEND
import android.content.Intent.ACTION_SEND_MULTIPLE
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.revik_o.common.factory.IntentFactory.createIntentCompat
import com.revik_o.common.utils.PermissionUtils.withPermissions
import com.revik_o.impl.AndroidAPI.Companion.initApplication
import com.revik_o.impl.AndroidAPI.Companion.isFirstApplicationRun
import com.revik_o.wdt.services.ApplicationBackgroundService
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        val context = this
        lifecycleScope.launch {
            if (isFirstApplicationRun(context)) {
                initApplication(context)
            }

            withPermissions(
                then = {
                    val intent = createIntentCompat(intent)
                    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                        v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                        )
                        insets
                    }

                    when (intent.action) {
                        ACTION_SEND_MULTIPLE -> {
                            intent.getParcelableArrayListExtraCompat(
                                Intent.EXTRA_STREAM,
                                Uri::class.java
                            )
                                .let { resources ->
                                    if (resources.isNotEmpty()) {
                                        startActivity(
                                            createIntentCompat(
                                                context,
                                                DevicesActivity::class.java
                                            )
                                                .sendResources(resources)
                                        )
                                    } else {
                                        TODO("Show message")
                                    }
                                }
                        }

                        ACTION_SEND -> {
                            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
                            val resource =
                                intent.getParcelableExtraCompat(Intent.EXTRA_STREAM, Uri::class.java)

                            when {
                                text != null -> TODO("Impl")
                                resource != null -> startActivity(
                                    createIntentCompat(context, DevicesActivity::class.java)
                                        .sendResources(ArrayList(listOf(resource)))
                                )

                                else -> TODO("Show message")
                            }
                        }

                        ACTION_GET_CONTENT -> TODO("investigate")
                        else -> {
                            startService(
                                Intent(context, ApplicationBackgroundService::class.java)
                            )
                            startActivity(Intent(context, MainActivity::class.java))
                        }
                    }
                },
                activity = context,
                permissions = arrayOf(
                    WRITE_EXTERNAL_STORAGE,
                    READ_EXTERNAL_STORAGE,
                    ACCESS_NETWORK_STATE,
                    POST_NOTIFICATIONS,
                    INTERNET
                )
            )
        }
    }
}