package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.WDTComponents.AppOption
import com.WDTComponents.DelegateMethods.IOpenDataMethod
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.SessionState
import java.io.File
import java.util.*

class OpenDataMethod: IOpenDataMethod {

    override fun processForClipboard(data: String) {
        if (data.contains("http")) {
            ContextCompat.getMainExecutor(SessionState.context).execute {
                SessionState.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data)))
            }
        }
    }

    override fun openFile(path: String) {
        ContextCompat.getMainExecutor(SessionState.context).execute {
            val intent = Intent(Intent.ACTION_VIEW)
            val tempPathToLowerCase = path.toLowerCase(Locale.ROOT)
            if (
                tempPathToLowerCase.contains(".png") or
                tempPathToLowerCase.contains(".jpg") or
                tempPathToLowerCase.contains(".jpeg") or
                tempPathToLowerCase.contains(".svg") or
                tempPathToLowerCase.contains(".raw")
            ) intent.setDataAndType(Uri.parse(path), "image/*")
            else intent.setDataAndType(Uri.parse(File(path).parentFile.absolutePath), "resource/folder")
            SessionState.context.startActivity(intent)
        }
    }

    override fun openFolderInFileManager(path: String) {
        ContextCompat.getMainExecutor(SessionState.context).execute {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(path), "resource/folder")
            SessionState.context.startActivity(intent)
        }
    }

    override fun openDownloadFolder() {
        openFolderInFileManager(AppOption.DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath)
    }

}