package ua.edu.onaft.wirelessdatatransmission_wdt.Observer.FileViewerActivity

import android.view.ViewGroup
import java.io.File

interface IFileViewerActivityObserver {

    fun setData(file: File?)

    fun setFolder(file: File)

    fun changeBackground(viewGroup: ViewGroup, idColor: Int)

    fun notifyActivity()

}