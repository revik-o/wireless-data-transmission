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
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Action.BackArrowButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.Method
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

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        constraintLayout = layoutInflater.inflate(R.layout.fragment_file_viewer_constraint_layout_main, null) as ViewGroup
        frameLayoutAppBar = constraintLayout.findViewById(R.id.fileViewerFragmentFrameLayoutAppBar)
        backArrowButton = constraintLayout.findViewById(R.id.fileViewerFragmentBackArrowButton)
        scrollView = constraintLayout.findViewById(R.id.fileViewerFragmentMainScrollView)
        scrollView.addView(mainLinearLayout)

        backArrowButton.setOnClickListener(BackArrowButtonOnClickListener(activity))

        Method.fillMainLinearLayoutForFileViewerFragment(
            activity,
            fileViewerViewModel,
            mainLinearLayout
        )

        return constraintLayout
    }

}