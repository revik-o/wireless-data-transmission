package ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel

import android.os.Build
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.ScreenDimension
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import ua.edu.onaft.wirelessdatatransmission_wdt.Observer.FileViewerObserver
import java.io.File

class FileViewerViewModel(arraySize: Int) {

    private val chosenFiles = ArrayList<File>()
    private val fileViewerObserver = FileViewerObserver(arraySize, this)
    private var mainLinearLayout: LinearLayout? = null
    lateinit var currentFile: File
    var inMainExternalStorage = false

    var onModeChoose = false

    private fun openDirectory(file: File) {
        inMainExternalStorage = file == Constant.mainExternalStorageDirectory
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        fileViewerObserver.render(file.listFiles())
    }

    private fun openFile(file: File) {}

    fun setLinearLayout(linearLayout: LinearLayout) {
        if (mainLinearLayout == null) mainLinearLayout = linearLayout
        fileViewerObserver.addLinearLayout(linearLayout)
    }

    fun addChosenFile(file: File) {
        chosenFiles.add(file)
        fileViewerObserver.addChosenCustomFrameLayout(file)
    }

    fun removeChosenFile(file: File) {
        chosenFiles.remove(file)
        fileViewerObserver.removeChosenCustomFrameLayout(file)
    }

    fun open(file: File) {
        currentFile = file
        if (file.exists()) {
            if (file.isDirectory) openDirectory(file)
            else openFile(file)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun turnToBack(): Boolean {
        return if (currentFile != Constant.mainExternalStorageDirectory || inMainExternalStorage) {
            if (!inMainExternalStorage) open(currentFile.parentFile!!)
            else {
                inMainExternalStorage = false
                Method.fillMainLinearLayoutForFileViewerFragment(SessionState.activity, ScreenDimension(SessionState.activity), this, mainLinearLayout!!)
            }
            true
        } else false
    }

}