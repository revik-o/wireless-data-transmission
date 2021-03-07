package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.os.Environment
import com.WDTlib.Action.SendType.ActionForSendType
import com.WDTlib.AppConfig
import com.WDTlib.AppOption
import com.WDTlib.IPWork.IPV4.PackageIPVersion4
import com.WDTlib.ServerControll.Server
import com.WDTlib.WorkingWithClient.StartForWorkingWithClient

import ua.edu.onaft.wirelessdatatransmission_wdt.Model.DeviceModelDAO

import java.io.File

class DefaultApplicationConfig {
    constructor() {
        /**
         *
         */
        AppConfig.AlertInterface.iLoadAlert = LoadAlertConfiguration().javaClass
        AppConfig.AlertInterface.iMessage = MessageConfiguration()
        AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase = WorkingWithDataBaseConfiguration()
        AppOption.Option.DIRECTORY_FOR_DOWNLOAD_FILES = File(Environment.getExternalStorageDirectory().absolutePath)
        /**
         *
         */
        AppConfig.Action.SendTypeInterface.iActionForSendType = ActionForSendType
        AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO = DeviceModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase)
        AppConfig.IPWorkInterface.IPV4.iIP = PackageIPVersion4()
        AppConfig.WorkingWithClientInterface.iWorkingWithClient = StartForWorkingWithClient()
        AppConfig.ServerControllInterface.iServer = Server()
        /**
         *
         */
        AppConfig.ServerControllInterface.iServer.startServerSocket()
    }
}