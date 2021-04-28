package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.annotation.SuppressLint
import android.app.Activity
import java.io.File

@SuppressLint("StaticFieldLeak")
object SessionState {

    lateinit var activity: Activity

    val files = ArrayList<File>()

}