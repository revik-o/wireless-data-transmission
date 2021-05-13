package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.annotation.SuppressLint
import android.content.Context
import java.io.File

@SuppressLint("StaticFieldLeak")
object SessionState {

    lateinit var context: Context

    val chosenFiles = ArrayList<File>()

    var sendType = 0

}