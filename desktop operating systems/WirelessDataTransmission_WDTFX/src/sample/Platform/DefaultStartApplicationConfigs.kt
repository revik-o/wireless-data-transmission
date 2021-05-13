package com.WDTComponents.StartApplicationConfigs

import javafx.application.Platform

import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.ModelDAO.DeviceModelDAO
import com.WDTComponents.Action.SendType.ActionForSendType
import com.WDTComponents.AlertInterfaces.ILoadAlert
import com.WDTComponents.AlertInterfaces.IMessage
import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption
import com.WDTComponents.DelegateMethods.IDelegateMethod
import com.WDTComponents.IPWork.IPV4.PackageIPVersion4
import com.WDTComponents.ServerControll.Server
import com.WDTComponents.SystemClipboard.ISystemClipboard
import com.WDTComponents.ThreadType.IUnusualThread
import com.WDTComponents.ThreadType.IUsualThread
import com.WDTComponents.WorkingWithClient.StartForWorkingWithClient
import sample.Platform.lM

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.io.File
import java.net.InetAddress

/**
 *
 */
class DefaultStartApplicationConfigs {

    constructor(iLoadAlert: ILoadAlert, iMessage: IMessage, iWorkingWithDataBase: IWorkingWithDataBase, directoryForDownloadFiles: File) {
        this.initInterfaces(iLoadAlert, iMessage, iWorkingWithDataBase, directoryForDownloadFiles)
    }

    fun start() {
        AppConfig.ServerControllInterface.iServer.startServerSocket()
    }

    private fun initInterfaces(iLoadAlert: ILoadAlert, iMessage: IMessage, iWorkingWithDataBase: IWorkingWithDataBase, directoryForDownloadFiles: File) {
        /**
         *
         */
        AppConfig.AlertInterface.iLoadAlert = iLoadAlert.javaClass
        AppConfig.AlertInterface.iMessage = iMessage
        AppConfig.AlertInterface.littleIMessage = lM()
        AppConfig.SystemClipboard.iSystemClipboard = object : ISystemClipboard {

            override fun setContent(string: String) {
                Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(string), null)
            }

            override fun getContent(): String = Toolkit.getDefaultToolkit().systemClipboard.getData(DataFlavor.stringFlavor) as String

        }
        AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase =  iWorkingWithDataBase
        AppOption.DIRECTORY_FOR_DOWNLOAD_FILES = directoryForDownloadFiles
        AppOption.DEVICE_TYPE = "COMPUTER"
        AppOption.LOCAL_DEVICE_NAME = "${System.getProperty("user.name")} ${System.getProperty("os.name")} ${InetAddress.getLocalHost().hostName}"
        /**
         *
         */
        AppConfig.Action.SendTypeInterface.iActionForSendType = ActionForSendType
        AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO = DeviceModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase)
        AppConfig.IPWorkInterface.IPV4.iIP = PackageIPVersion4()
        AppConfig.WorkingWithClientInterface.iWorkingWithClient = StartForWorkingWithClient()
        AppConfig.ServerControllInterface.iServer = Server()
    }

    /**
     * As:
     * @Overide
     * protected void finalize() throws Throwable {
     *     // Some code
     * }
     */
    protected fun finalize() {
        println("Default configurations is finished")
    }

}