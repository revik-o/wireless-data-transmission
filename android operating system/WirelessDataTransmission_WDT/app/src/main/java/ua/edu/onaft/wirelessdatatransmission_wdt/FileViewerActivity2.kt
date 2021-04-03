package ua.edu.onaft.wirelessdatatransmission_wdt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState
import ua.edu.onaft.wirelessdatatransmission_wdt.ViewModel.FileViewerActivity.FileViewerActivityViewModel

@Deprecated("")
class FileViewerActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer2)

        StaticState.activity = this

        FileViewerActivityViewModel().addViewGroup(
                findViewById(R.id.fileViewerHorizontalScrollViewLinearLayout),
                findViewById(R.id.fileViewerScrollViewLinearLayout),
                findViewById(R.id.fileViewerTextView),
                findViewById(R.id.fileViewerFloatingActionButton)
        )
    }
}