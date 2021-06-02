package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.annotation.SuppressLint
import android.content.Context
import com.WDTComponents.ArgClass.FileInfo
import java.io.File
import java.io.InputStream

@SuppressLint("StaticFieldLeak")
object SessionState {

    lateinit var context: Context

    val chosenFiles = ArrayList<File>()

    val fileInfoArrayList = ArrayList<FileInfo>()

    var sendType = 0

}