package sample.WDTComponents.StartApplicationConfigs

import sample.DataBase.IWorkingWithDataBase
import sample.DataBase.ModelDAO.DeviceModelDAO
import sample.WDTComponents.Action.SendType.ActionForSendType
import sample.WDTComponents.AlertInterfaces.ILoadAlert
import sample.WDTComponents.AlertInterfaces.IMessage
import sample.WDTComponents.AppConfig
import sample.WDTComponents.AppOption
import sample.WDTComponents.IPWork.IPV4.PackageIPVersion4
import sample.WDTComponents.ServerControll.Server
import sample.WDTComponents.WorkingWithClient.StartForWorkingWithClient
import java.io.File

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
        AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase =  iWorkingWithDataBase
        AppOption.DIRECTORY_FOR_DOWNLOAD_FILES = directoryForDownloadFiles
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