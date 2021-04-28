package ua.edu.onaft.wirelessdatatransmission_wdt.Observer

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment.DirectoryOrFileLongClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment.DirectoryOrFileOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.CustomFrameLayout
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.ScreenDimension
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel
import java.io.File

class FileViewerObserver(arraySize: Int, fileViewerViewModel: FileViewerViewModel) {

    private val fileViewerViewModel: FileViewerViewModel = fileViewerViewModel
    private val linearLayouts = Array<LinearLayout?>(arraySize) { null }
    private var chosenCustomFrameLayouts = ArrayList<CustomFrameLayout>()
    private var currentCustomFrameLayouts = Array<CustomFrameLayout?>(0) { null }
    private var spaceHeights = listOf(Constant.specialSpace, Constant.usualSpace)

    private fun addViewToMainLinearLayout(indexOfCurrentArray: Int, customFrameLayoutArg: CustomFrameLayout) {
        Thread {
            val customFrameLayout = CustomFrameLayout(SessionState.activity, ScreenDimension(SessionState.activity), customFrameLayoutArg.file, customFrameLayoutArg.isFile, customFrameLayoutArg.text)
            customFrameLayout.isChosen = customFrameLayoutArg.isChosen
            customFrameLayout.frameLayout.setOnClickListener(DirectoryOrFileOnClickListener(SessionState.activity, fileViewerViewModel, customFrameLayout))
            customFrameLayout.frameLayout.setOnLongClickListener(DirectoryOrFileLongClickListener(SessionState.activity, fileViewerViewModel, customFrameLayout))
            currentCustomFrameLayouts[indexOfCurrentArray] = customFrameLayout
            SessionState.activity.runOnUiThread {
                linearLayouts[0]?.addView(customFrameLayout.frameLayout)
            }
        }.start()

    }

    private fun removeAllAndAddSpaceToLinearLayout(idLinearLayout: Int) {
        val linearLayout: LinearLayout? = linearLayouts[idLinearLayout]
        val spaceHeight: Int = spaceHeights[idLinearLayout]
        if (linearLayout != null) {
            linearLayout.removeAllViews()
            val newSpace = Space(SessionState.activity)
            newSpace.layoutParams = ViewGroup.LayoutParams(0, spaceHeight)
            linearLayout.addView(newSpace)
        }
    }

    fun addLinearLayout(linearLayout: LinearLayout) {
        for (i in linearLayouts.indices)
            if (linearLayouts[i] == null) {
                linearLayouts[i] = linearLayout
                break
            }
    }

    fun addChosenCustomFrameLayout(file: File) {
        val customFrameLayout = CustomFrameLayout(SessionState.activity, ScreenDimension(SessionState.activity), file, file.isFile, file.name)
        chosenCustomFrameLayouts.add(customFrameLayout)
        customFrameLayout.isChosen = true
        customFrameLayout.frameLayout.setOnClickListener(DirectoryOrFileOnClickListener(SessionState.activity, fileViewerViewModel, customFrameLayout))
        linearLayouts[1]?.addView(customFrameLayout.frameLayout)
    }

    @SuppressLint("ResourceType")
    fun removeChosenCustomFrameLayout(file: File) {
        for (value in chosenCustomFrameLayouts)
            if (value.file == file) {
                chosenCustomFrameLayouts.remove(value)
                for (currentCustomFrameLayout in currentCustomFrameLayouts)
                    if (value.file == currentCustomFrameLayout?.file) {
                        currentCustomFrameLayout.isChosen = false
                        break
                    }
                update()
                break
            }
    }

    fun render(fileArray: Array<File>) {
        val tempListOfChosenCustomFrameLayouts = ArrayList<CustomFrameLayout>(chosenCustomFrameLayouts.size)
        chosenCustomFrameLayouts.forEach { tempListOfChosenCustomFrameLayouts.add(it) }
        currentCustomFrameLayouts = Array(fileArray.size) { null }
        /**
         * TODO???
         */
        val screenDimension = ScreenDimension(SessionState.activity)
        removeAllAndAddSpaceToLinearLayout(0)
        mainLoop@for ((index, file) in fileArray.withIndex()) {
            for (value in tempListOfChosenCustomFrameLayouts) {
                if (value.file == file) {
                    addViewToMainLinearLayout(index, value)
                    tempListOfChosenCustomFrameLayouts.remove(value)
                    continue@mainLoop
                }
            }
            addViewToMainLinearLayout(index, CustomFrameLayout(SessionState.activity, screenDimension, file, file.isFile, file.name))
        }
    }

    fun update() {
        removeAllAndAddSpaceToLinearLayout(1)
        chosenCustomFrameLayouts.forEach { linearLayouts[1]!!.addView(it.frameLayout) }
    }

}