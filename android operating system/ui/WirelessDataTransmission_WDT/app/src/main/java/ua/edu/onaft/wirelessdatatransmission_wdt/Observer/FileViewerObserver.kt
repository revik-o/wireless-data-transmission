package ua.edu.onaft.wirelessdatatransmission_wdt.Observer

import android.annotation.SuppressLint
import android.app.Activity
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Space
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment.DirectoryOrFileLongClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment.DirectoryOrFileOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.CustomFrameLayout
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.ScreenDimension
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import ua.edu.onaft.wirelessdatatransmission_wdt.R
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel
import java.io.File

class FileViewerObserver(activity: Activity, arraySize: Int, fileViewerViewModel: FileViewerViewModel) {

    private val activity: Activity = activity
    private val fileViewerViewModel: FileViewerViewModel = fileViewerViewModel
    private val linearLayouts = Array<LinearLayout?>(arraySize) { null }
    private var chosenCustomFrameLayouts = ArrayList<CustomFrameLayout>()
    private var currentCustomFrameLayouts = Array<CustomFrameLayout?>(0) { null }
    private var spaceHeights = listOf(Constant.specialSpace, Constant.usualSpace)

    private fun addViewToMainLinearLayout(indexOfCurrentArray: Int, customFrameLayoutArg: CustomFrameLayout) {
        val customFrameLayout = CustomFrameLayout(
            activity,
//                ScreenDimension(SessionState.activity),
            customFrameLayoutArg.file,
            customFrameLayoutArg.isFile,
            customFrameLayoutArg.text
        )
        customFrameLayout.frameLayout.startAnimation(AnimationUtils.loadAnimation(SessionState.context, R.anim.custom_button_open_animation))
        customFrameLayout.isChosen = customFrameLayoutArg.isChosen
        customFrameLayout.frameLayout.setOnClickListener(DirectoryOrFileOnClickListener(activity, fileViewerViewModel, customFrameLayout))
        customFrameLayout.frameLayout.setOnLongClickListener(DirectoryOrFileLongClickListener(activity, fileViewerViewModel, customFrameLayout))
        currentCustomFrameLayouts[indexOfCurrentArray] = customFrameLayout
        linearLayouts[0]?.addView(customFrameLayout.frameLayout)
    }

    private fun removeAllAndAddSpaceToLinearLayout(idLinearLayout: Int) {
        val linearLayout: LinearLayout? = linearLayouts[idLinearLayout]
        val spaceHeight: Int = spaceHeights[idLinearLayout]
        if (linearLayout != null) {
            linearLayout.removeAllViews()
            val newSpace = Space(SessionState.context)
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
        val customFrameLayout = CustomFrameLayout(
            activity,
//                ScreenDimension(SessionState.activity),
            file,
            file.isFile,
            file.name
        )
        chosenCustomFrameLayouts.add(customFrameLayout)
        customFrameLayout.isChosen = true
        customFrameLayout.frameLayout.setOnClickListener(DirectoryOrFileOnClickListener(activity, fileViewerViewModel, customFrameLayout))
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
        val screenDimension = ScreenDimension(activity)
        removeAllAndAddSpaceToLinearLayout(0)
        mainLoop@for ((index, file) in fileArray.withIndex()) {
            for (value in tempListOfChosenCustomFrameLayouts) {
                if (value.file == file) {
                    addViewToMainLinearLayout(index, value)
                    tempListOfChosenCustomFrameLayouts.remove(value)
                    continue@mainLoop
                }
            }
            addViewToMainLinearLayout(index, CustomFrameLayout(
                activity,
//                    screenDimension,
                file,
                file.isFile,
                file.name
            ))
        }
    }

    fun update() {
        removeAllAndAddSpaceToLinearLayout(1)
        chosenCustomFrameLayouts.forEach { linearLayouts[1]!!.addView(it.frameLayout) }
    }

}