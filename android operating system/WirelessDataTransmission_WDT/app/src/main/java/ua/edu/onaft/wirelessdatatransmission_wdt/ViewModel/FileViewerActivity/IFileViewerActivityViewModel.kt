package ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerActivity

import android.view.View
import android.view.ViewGroup
import java.io.File

interface IFileViewerActivityViewModel {

    fun addViewGroup(horizontalScrollViewLinearLayout: ViewGroup, scrollViewLinearLayout: ViewGroup, fileViewerTextView: View, fileViewerFloatingActionButton: View)

    fun openDirectory(file: File)

    fun openFile(file: File)

    fun textViewOnClickListener(file: File, viewGroup: ViewGroup): View.OnClickListener

    fun textViewOnLongClickListener(file: File, viewGroup: ViewGroup): View.OnLongClickListener

    fun buttonOnClickListener(): View.OnClickListener

}