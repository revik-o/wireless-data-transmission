package ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import ua.edu.onaft.wirelessdatatransmission_wdt.R
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerViewModel

class FileViewerListOfSelectedFilesFragment(activity: Activity, fileViewerViewModel: FileViewerViewModel, linearLayout: LinearLayout): Fragment() {

    private val activity: Activity = activity
    private val fileViewerViewModel: FileViewerViewModel = fileViewerViewModel
    private lateinit var scrollView: ViewGroup
    private lateinit var constraintLayout: ViewGroup
    private lateinit var frameLayoutAppBar: FrameLayout
    private val mainLinearLayout: ViewGroup = linearLayout

    override fun onStart() {
//        val screenDimension = ScreenDimension(activity)
//        val height = screenDimension.height
//        val width = screenDimension.width

//        frameLayoutAppBar.layoutParams.height = (height / 14.55).toInt()

//        space.layoutParams.height = Constant.usualSpace
//        mainLinearLayout.removeAllViews()

//        val space = Space(activity)
//        space.layoutParams = ViewGroup.LayoutParams(0, Constant.usualSpace)

//        (mainLinearLayout as LinearLayout).orientation = LinearLayout.VERTICAL
//        mainLinearLayout.addView(space)


        super.onStart()
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        constraintLayout = layoutInflater.inflate(R.layout.fragment_file_viewer_list_of_selected_files, null) as ViewGroup
        frameLayoutAppBar = constraintLayout.findViewById(R.id.selectedFileViewerFrameLayoutAppBar)
        scrollView = constraintLayout.findViewById(R.id.selectedFileViewerFragmentMainScrollView)

        scrollView.addView(mainLinearLayout)

        return constraintLayout
    }

}