package app.Configuration;

import com.WDTComponents.SystemClipboard.ClipboardFile;
import com.WDTComponents.SystemClipboard.ContentType;
import com.WDTComponents.SystemClipboard.ISystemClipboard;
import com.WDTComponents.TypeDeviceENUM;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class SystemClipboardConfiguration implements ISystemClipboard {

    @Override
    public void setContent(String string) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(string), null);
    }

    @Override
    public String getTypeContent() {
        return ContentType.INSTANCE.getTEXT();
    }

    @Override
    public String getTextContent() {
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public ClipboardFile getContentFile() {
        return new ClipboardFile();
    }

}