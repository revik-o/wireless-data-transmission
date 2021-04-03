package ua.edu.onaft.wirelessdatatransmission_wdt.Commont

import android.app.Activity
import android.os.Build
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class CustomFrameLayout(activity: Activity, screenDimension: ScreenDimension, background: Int, backgroundColor: Int, text: String) {

    private val activity: Activity = activity
    private val screenDimension: ScreenDimension = screenDimension
    private val background: Int = background
    private val backgroundColor: Int = backgroundColor
    private val text: String = text

    val frameLayout = FrameLayout(activity)

    fun create(): ViewGroup {
        val imageView = ImageView(activity)
        val textView = TextView(activity)

        frameLayout.layoutParams =  FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        frameLayout.setBackgroundColor(background)

//        imageView.setImageResource(imageResource)

        imageView.layoutParams = FrameLayout.LayoutParams((screenDimension.height / 14.55).toInt(), (screenDimension.height / 14.55).toInt())
        (imageView.layoutParams as FrameLayout.LayoutParams).setMargins(screenDimension.width / 90, 0, 0, 0)

        textView.textSize = 30f
        textView.text = text
        textView.setBackgroundColor(backgroundColor)
        textView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        (textView.layoutParams as FrameLayout.LayoutParams).setMargins((imageView.layoutParams as FrameLayout.LayoutParams).leftMargin + imageView.layoutParams.width + 5, (frameLayout.layoutParams.height / 2).toInt(), 0, 0)

        frameLayout.addView(imageView)
        frameLayout.addView(textView)

        return frameLayout
    }

}