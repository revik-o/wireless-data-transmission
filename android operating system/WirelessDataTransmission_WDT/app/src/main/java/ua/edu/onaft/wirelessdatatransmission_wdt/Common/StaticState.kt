package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.annotation.SuppressLint
import android.app.Activity

import java.io.File

@SuppressLint("StaticFieldLeak")
object StaticState {

    lateinit var activity: Activity

    var fileSet: Set<File>? = null

}