package ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.Fragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.core.view.updateMargins
import androidx.fragment.app.Fragment
import ua.edu.onaft.wirelessdatatransmission_wdt.Commont.Action.BackArrowButtonOnClickListener
import ua.edu.onaft.wirelessdatatransmission_wdt.Commont.CustomFrameLayout
import ua.edu.onaft.wirelessdatatransmission_wdt.Commont.ScreenDimension
import ua.edu.onaft.wirelessdatatransmission_wdt.R

class FileViewerFragment(activity: Activity): Fragment() {

    private val activity: Activity = activity

    private lateinit var constraintLayout: ViewGroup
    private lateinit var frameLayoutAppBar: FrameLayout
    private lateinit var backArrowButton: View
    private lateinit var mainLinearLayout: ViewGroup
    private lateinit var space: View

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStart() {
        val screenDimension = ScreenDimension(activity)
        val height = screenDimension.height
        val width = screenDimension.width

        frameLayoutAppBar.layoutParams.height = (height / 14.55).toInt()
        backArrowButton.layoutParams.height = (frameLayoutAppBar.layoutParams.height / 1.375).toInt()
        backArrowButton.layoutParams.width = backArrowButton.layoutParams.height
        (backArrowButton.layoutParams as FrameLayout.LayoutParams).updateMargins(left = width / 45, bottom = frameLayoutAppBar.layoutParams.height / 11)

        backArrowButton.setOnClickListener(BackArrowButtonOnClickListener(activity))

        val customFrameLayoutForDeviceStorage = CustomFrameLayout(activity, screenDimension, android.R.attr.selectableItemBackgroundBorderless, R.drawable.ic_baseline_folder_24, Build.MODEL)
        customFrameLayoutForDeviceStorage.create().setOnClickListener(null)
        customFrameLayoutForDeviceStorage.frameLayout.setOnLongClickListener(null)
        mainLinearLayout.addView(customFrameLayoutForDeviceStorage.frameLayout)


        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        constraintLayout = layoutInflater.inflate(R.layout.fragment_file_viewer_constraint_layout_main, null) as ViewGroup
        frameLayoutAppBar = constraintLayout.findViewById(R.id.fileViewerFragmentFrameLayoutAppBar)
        backArrowButton = constraintLayout.findViewById(R.id.fileViewerFragmentBackArrowButton)
        mainLinearLayout = constraintLayout.findViewById(R.id.fileViewerFragmentMainLinearLayout)
        space = constraintLayout.findViewById(R.id.fileViewerFragmentSpace)
        return constraintLayout
    }

}