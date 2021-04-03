package ua.edu.onaft.wirelessdatatransmission_wdt.Observer.FileViewerActivity

import android.annotation.SuppressLint
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.size

import ua.edu.onaft.wirelessdatatransmission_wdt.Observer.IObserver
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState
import ua.edu.onaft.wirelessdatatransmission_wdt.R
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerActivity.IFileViewerActivityViewModel

import java.io.File
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FileViewerActivityObserver(
        horizontalScrollViewLinearLayout: ViewGroup,
        scrollViewLinearLayout: ViewGroup,
        fileViewerTextView: View,
        fileViewerFloatingActionButton: View,
        fileViewerActivityViewModel: IFileViewerActivityViewModel
): IFileViewerActivityObserver, IObserver {

    private val horizontalScrollViewLinearLayout: ViewGroup = horizontalScrollViewLinearLayout
    private val scrollViewLinearLayout: ViewGroup = scrollViewLinearLayout
    private val fileViewerTextView: View = fileViewerTextView
    private val fileViewerFloatingActionButton: View = fileViewerFloatingActionButton
    private val fileViewerActivityViewModel: IFileViewerActivityViewModel = fileViewerActivityViewModel

    @SuppressLint("ResourceAsColor")
    override fun setData(file: File?) {
        if (file != null) {
            val iconImageView = ImageView(StaticState.activity.applicationContext)
            val nameFileTextView = TextView(StaticState.activity.applicationContext)
            val frameLayout = FrameLayout(StaticState.activity.applicationContext)

            // config iconImageView
            iconImageView.layoutParams = ViewGroup.LayoutParams(70, ViewGroup.LayoutParams.MATCH_PARENT)

            iconImageView.setImageResource(if (file?.isDirectory) R.drawable.ic_baseline_folder_24 else R.drawable.ic_baseline_insert_drive_file_24)

            // config nameFileTextView
            nameFileTextView.text = file.name
            nameFileTextView.textSize = 24f
            nameFileTextView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL)
            (nameFileTextView.layoutParams as FrameLayout.LayoutParams).leftMargin = 80

            // config frameLayout
            frameLayout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 200)
            frameLayout.setBackgroundColor(R.color.white)
            frameLayout.setOnLongClickListener(fileViewerActivityViewModel.textViewOnLongClickListener(file, frameLayout))
            frameLayout.setOnClickListener(fileViewerActivityViewModel.textViewOnClickListener(file, frameLayout))
            frameLayout.addView(iconImageView)
            frameLayout.addView(nameFileTextView)

            scrollViewLinearLayout.addView(frameLayout)
        }
    }

    override fun setFolder(file: File) {
        val arrowTextView = TextView(StaticState.activity.applicationContext)
        arrowTextView.text = " > "
        arrowTextView.textSize = 30f

        val fileTextViewForHorizontalLinearLayout = TextView(StaticState.activity.applicationContext)
        fileTextViewForHorizontalLinearLayout.text = file.name
        fileTextViewForHorizontalLinearLayout.textSize = 30f
        fileTextViewForHorizontalLinearLayout.setOnClickListener {
            update()
            notifyActivity()
            val arrayList = ArrayList<File>()
            var f = file
            while (!f.absolutePath.equals(Environment.getExternalStorageDirectory().absolutePath)) {
                arrayList.add(file)
                f = f.parentFile
            }
            if (arrayList.size != 0)
                for (i in arrayList.size - 1..0)
                    setFolder(arrayList[i])

            fileViewerActivityViewModel.openDirectory(file)
        }
        horizontalScrollViewLinearLayout.addView(arrowTextView)
        horizontalScrollViewLinearLayout.addView(fileTextViewForHorizontalLinearLayout)
    }

    @SuppressLint("ResourceAsColor")
    override fun changeBackground(viewGroup: ViewGroup, idColor: Int) {
        viewGroup.setBackgroundColor(idColor)
    }

    override fun notifyActivity() {
        scrollViewLinearLayout.removeAllViews()
    }

    override fun update() {
        horizontalScrollViewLinearLayout.removeAllViews()
    }



}
