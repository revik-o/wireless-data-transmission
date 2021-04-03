package ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerActivity

import android.content.Intent
import android.os.Environment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ua.edu.onaft.wirelessdatatransmission_wdt.Observer.FileViewerActivity.FileViewerActivityObserver
import ua.edu.onaft.wirelessdatatransmission_wdt.Observer.FileViewerActivity.IFileViewerActivityObserver
import ua.edu.onaft.wirelessdatatransmission_wdt.ScanDevise
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState
import ua.edu.onaft.wirelessdatatransmission_wdt.R
import ua.edu.onaft.wirelessdatatransmission_wdt.WelcomeActivity

import java.io.File

class FileViewerActivityViewModel: IFileViewerActivityViewModel {

    private lateinit var fileViewerActivityObserver: IFileViewerActivityObserver
    private val fileSet: HashSet<File> = HashSet()

    /**
     * mode = 0 -> open something
     * mode = 1 -> change mode
     */
    private var mode = 0

    private fun open(file: File) {
        if (file.exists()){
            if (file.isDirectory)
                openDirectory(file)
            else
                openFile(file)
        }
    }

    private fun select(file: File, viewGroup: ViewGroup) {
        if (fileSet.contains(file)) {
            fileSet.remove(file)
            fileViewerActivityObserver.changeBackground(viewGroup, R.color.white)
        } else {
            fileSet.add(file)
            fileViewerActivityObserver.changeBackground(viewGroup, R.color.gray)
        }
    }

    override fun addViewGroup(
            horizontalScrollViewLinearLayout: ViewGroup,
            scrollViewLinearLayout: ViewGroup,
            fileViewerTextView: View,
            fileViewerFloatingActionButton: View
    ) {
        fileViewerFloatingActionButton.setOnClickListener(this.buttonOnClickListener())
        fileViewerActivityObserver = FileViewerActivityObserver(horizontalScrollViewLinearLayout, scrollViewLinearLayout, fileViewerTextView, fileViewerFloatingActionButton, this)
        openDirectory(Environment.getExternalStorageDirectory())
    }

    override fun openDirectory(file: File) {
        fileViewerActivityObserver.notifyActivity()
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val fileArray: Array<File>? = file.listFiles()
        fileViewerActivityObserver.setFolder(file)
        if (fileArray != null) fileArray.forEach { fileViewerActivityObserver.setData(it) } else fileViewerActivityObserver.setData(null)
    }

    override fun openFile(file: File) {
    }

    override fun textViewOnClickListener(file: File, viewGroup: ViewGroup): View.OnClickListener {
        return View.OnClickListener {
            when (mode) {
                0 -> open(file)
                1 -> select(file, viewGroup)
            }
        }
    }

    override fun textViewOnLongClickListener(file: File, viewGroup: ViewGroup): View.OnLongClickListener {
        return View.OnLongClickListener {
            when (mode) {
                0 -> {
                    mode = 1
                    select(file, viewGroup)
                }
                1 -> open(file)
            }
            true
        }
    }

    override fun buttonOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            if (fileSet.size != 0) {
                StaticState.activity.startActivity(Intent(StaticState.activity.applicationContext, ScanDevise::class.java))
                StaticState.activity.finish()
                StaticState.fileSet = fileSet
            }
            else {
                Toast.makeText(StaticState.activity, "Select data", Toast.LENGTH_LONG).show()
            }
        }
    }

}