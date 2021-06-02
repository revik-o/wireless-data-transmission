package sample.Platform;

import com.WDTComponents.AlertInterfaces.ILittleMessage;
import com.WDTComponents.AppOption;
import sampleimagePath.AppInfoKt;

public class MessageForNotifyAboutCompleteDownloadProcess
        implements ILittleMessage
{

    {
        System.loadLibrary("WDTMessageForNotifyAboutCompleteDownloadProcess");
    }

    native void showNativeNotifyMessage(
            String mainTitle, String averageText, String smallText, String mainImagePath
            );

    @Override
    public void showMessage(String strMessage) {
        showNativeNotifyMessage("WDT", strMessage, "Files stay at " +
                AppOption.DIRECTORY_FOR_DOWNLOAD_FILES.getAbsolutePath(), AppInfoKt.ICON);
    }
}
