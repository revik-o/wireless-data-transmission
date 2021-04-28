package ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.core.view.updateMargins
import androidx.fragment.app.Fragment
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment.DirectoryOrFileLongClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.ActionView.FileViewerActivity.ActionFragment.DirectoryOrFileOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Action.BackArrowButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Constant
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.CustomFrameLayout
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.ScreenDimension
import ua.edu.onaft.wirelessdatatransmission_wdt.R
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel

class FileViewerFragment(activity: Activity, fileViewerViewModel: FileViewerViewModel, linearLayout: LinearLayout): Fragment() {

    private val activity: Activity = activity
    private val fileViewerViewModel: FileViewerViewModel = fileViewerViewModel

    private lateinit var constraintLayout: ViewGroup
    private lateinit var frameLayoutAppBar: FrameLayout
    private lateinit var backArrowButton: View
    private lateinit var scrollView: ViewGroup
    private var mainLinearLayout: ViewGroup = linearLayout
//    private lateinit var space: View

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onStart() {
        val screenDimension = ScreenDimension(activity)
        val height = screenDimension.height
        val width = screenDimension.width

        frameLayoutAppBar.layoutParams.height = (height / 14.55).toInt()
        backArrowButton.layoutParams.height = (frameLayoutAppBar.layoutParams.height / 1.375).toInt()
        backArrowButton.layoutParams.width = backArrowButton.layoutParams.height
        (backArrowButton.layoutParams as FrameLayout.LayoutParams).updateMargins(left = width / 45, bottom = frameLayoutAppBar.layoutParams.height / 11)

//        Constant.specialSpace = frameLayoutAppBar.layoutParams.height * 2

//        val space = Space(activity)
//        space.layoutParams = ViewGroup.LayoutParams(0, Constant.specialSpace)

//        (mainLinearLayout as LinearLayout).orientation = LinearLayout.VERTICAL
//        mainLinearLayout.addView(space)

        scrollView.removeAllViews()
        scrollView.addView(mainLinearLayout)

        backArrowButton.setOnClickListener(BackArrowButtonOnClickListener(activity))

        Method.fillMainLinearLayoutForFileViewerFragment(activity, screenDimension, fileViewerViewModel, mainLinearLayout)
        super.onStart()
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        constraintLayout = layoutInflater.inflate(R.layout.fragment_file_viewer_constraint_layout_main, null) as ViewGroup
        frameLayoutAppBar = constraintLayout.findViewById(R.id.fileViewerFragmentFrameLayoutAppBar)
        backArrowButton = constraintLayout.findViewById(R.id.fileViewerFragmentBackArrowButton)
        scrollView = constraintLayout.findViewById(R.id.fileViewerFragmentMainScrollView)
//        mainLinearLayout = constraintLayout.findViewById(R.id.fileViewerFragmentMainLinearLayout)
//        space = constraintLayout.findViewById(R.id.fileViewerFragmentSpace)

//        fileViewerViewModel.setLinearLayout(mainLinearLayout as LinearLayout)

        return constraintLayout
    }

}