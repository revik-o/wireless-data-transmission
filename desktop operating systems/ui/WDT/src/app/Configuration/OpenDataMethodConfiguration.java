package app.Configuration;

import com.WDTComponents.AppOption;
import com.WDTComponents.DelegateMethods.IOpenDataMethod;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenDataMethodConfiguration implements IOpenDataMethod {

    @Override
    public void processForClipboard(String data) {
        if (data.contains("http")) {
            try {
                if (System.getProperty("os.name").toLowerCase().contains("linux"))
                    Runtime.getRuntime().exec("xdg-open " + data);
                else
                    Desktop.getDesktop().browse(new URI(data));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void openFile(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openFolderInFileManager(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openDownloadFolder() {
        try {
            Desktop.getDesktop().open(AppOption.DIRECTORY_FOR_DOWNLOAD_FILES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}