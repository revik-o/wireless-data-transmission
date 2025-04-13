package com.revik_o.android.test

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.revik_o.android.test.ui.theme.WirelessDataTransmissionTheme
import com.revik_o.common.utils.PermissionUtils.withPermissions
import com.revik_o.impl.service.DownloadStorageService
import com.revik_o.infrastructure.common.dtos.RemoteResourceData

class TestActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        withPermissions(
            then = {
                val data = "yaml content".toByteArray()
                DownloadStorageService(this).createResourceOutputStream(
                    RemoteResourceData(
                        "WDT1/file.yaml",
                        data.size.toLong()
                    )
                )?.let { kek ->
                    kek.write(data, 0, data.size)
                    kek.close()
                }
            },
            activity = this,
            permissions = arrayOf(
                WRITE_EXTERNAL_STORAGE,
                READ_EXTERNAL_STORAGE
            )
        )

        setContent {
            WirelessDataTransmissionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WirelessDataTransmissionTheme {
        Greeting("Android")
    }
}