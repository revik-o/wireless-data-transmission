package ua.edu.onaft.wirelessdatatransmission_wdt.IndividualConfigs.FileViewerActivity.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import ua.edu.onaft.wirelessdatatransmission_wdt.R

class FileViewerListOfSelectedFilesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.activity_file_viewer_list_of_selected_files, null)
    }

}