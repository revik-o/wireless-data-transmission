package sample.Platform;

import com.WDTComponents.AlertInterfaces.ILittleMessage;

import java.io.IOException;

public class lM implements ILittleMessage {

    @Override
    public void showMessage(String strMessage) {
        try {
            String s = "notify-send \"WDT\" \"" + strMessage + "\"";
            Runtime.getRuntime().exec(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
