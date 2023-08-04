package ua.edu.onaft.wirelessdatatransmission_wdt.Common

import android.app.Activity
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ua.edu.onaft.wirelessdatatransmission_wdt.R
import java.io.File

class CustomFrameLayout(
    activity: Activity,
//        screenDimension: ScreenDimension,
    file: File,
    isFile:
    Boolean,
    text: String) {

    val frameLayout: ViewGroup = LayoutInflater.from(activity).inflate(R.layout.layout_custom, null) as ViewGroup

    private val activity: Activity = activity
    //    private val screenDimension: ScreenDimension = screenDimension
    val file: File = file
    val isFile: Boolean = isFile
    val text: String = text

    var isChosen: Boolean = false
        set(value) {
            val typedValue = TypedValue()
            activity.runOnUiThread {
                activity.theme.resolveAttribute(if (value) R.attr.colorOnSurface else (R.attr.selectableItemBackground), typedValue, true)
                frameLayout.setBackgroundColor(typedValue.data)
            }
            field = value
        }

    init {
        val imageView: ImageView = frameLayout.findViewById(R.id.folderImageView)
        val textView: TextView = frameLayout.findViewById(R.id.folderTextView)

        adapt(imageView, textView)

        val typedValue = TypedValue()
        activity.theme.resolveAttribute(R.attr.selectableItemBackground, typedValue, true)
        frameLayout.setBackgroundColor(typedValue.data)
    }

    private fun adapt(imageView: ImageView, textView: TextView) {
        imageView.setImageResource(if (isFile) R.drawable.ic_file_24 else R.drawable.ic_baseline_folder_24)
//        imageView.layoutParams = FrameLayout.LayoutParams((screenDimension.height / 14.55).toInt(), (screenDimension.height / 14.55).toInt())
//        (imageView.layoutParams as FrameLayout.LayoutParams).setMargins(screenDimension.width / 90, 0, 0, 0)

        textView.textSize = 30f
        textView.text = text
//        (textView.layoutParams as FrameLayout.LayoutParams).setMargins((imageView.layoutParams as FrameLayout.LayoutParams).leftMargin + imageView.layoutParams.width + 5, imageView.layoutParams.height / 7, 0, 0)
    }

}