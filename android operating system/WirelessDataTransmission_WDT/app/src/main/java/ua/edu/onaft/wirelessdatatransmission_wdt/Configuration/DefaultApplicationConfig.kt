package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.os.Environment

import com.WDTComponents.Action.SendType.ActionForSendType
import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption
import com.WDTComponents.DataBase.ModelDAO.DeviceModelDAO
import com.WDTComponents.DelegateMethods.IDelegateMethod
import com.WDTComponents.IPWork.IPV4.PackageIPVersion4
import com.WDTComponents.ServerControll.Server
import com.WDTComponents.WorkingWithClient.StartForWorkingWithClient

import java.io.File
import java.net.InetAddress

class DefaultApplicationConfig {
    constructor() {
        /**
         *
         */
        AppConfig.AlertInterface.errorIMessage = ErrorMessageConfiguration()
        AppConfig.AlertInterface.iLoadAlert = LoadAlertConfiguration().javaClass
        AppConfig.AlertInterface.iMessage = MessageConfiguration()
        AppConfig.AlertInterface.littleIMessage = LittleMessageConfiguration()
        AppConfig.SystemClipboard.iSystemClipboard = SystemClipboardConfiguration()
//        AppConfig.ThreadType.iUnusualThread = UnusualThreadConfiguration()
        AppConfig.ThreadType.iUsualThread = UsualThreadConfiguration()
        AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase = WorkingWithDataBaseConfiguration()
        AppConfig.ThreadType.iUsualThread.execute(object : IDelegateMethod {
            override fun voidMethod() {
                AppOption.DEVICE_TYPE = "PHONE"
                AppOption.LOCAL_DEVICE_NAME = "${System.getProperty("user.name")} ${System.getProperty("os.name")} ${InetAddress.getLocalHost().hostName}"
                AppOption.DIRECTORY_FOR_DOWNLOAD_FILES = File(Environment.getExternalStorageDirectory().absolutePath + "/Download/Wireless Data Transmission")
            }
        })
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