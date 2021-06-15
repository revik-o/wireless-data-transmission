package com.WDTComponents.Configuration

import com.WDTComponents.Action.SendType.ActionForSendType
import com.WDTComponents.AlertInterfaces.ILittleMessage
import com.WDTComponents.AlertInterfaces.ILoadAlert
import com.WDTComponents.AlertInterfaces.IMessage
import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption
import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.ModelDAO.*
import com.WDTComponents.DelegateMethods.IDelegateMethodReturnFile
import com.WDTComponents.DelegateMethods.IDelegateMethodReturnString
import com.WDTComponents.DelegateMethods.IOpenDataMethod
import com.WDTComponents.IPWork.IPV4.PackageIPVersion4
import com.WDTComponents.ServerControll.Server
import com.WDTComponents.SystemClipboard.ISystemClipboard
import com.WDTComponents.WorkingWithClient.StartForWorkingWithClient

class WDTMinDefaultConfiguration(
    errorMessageConfig: ILittleMessage,
    loadAlertConfigClass: Class<*>,
    messageConfig: IMessage,
    littleMessageConfig: ILittleMessage,
    notifyAboutCompleteProcessConfig: ILittleMessage,
    systemClipboardConfig: ISystemClipboard,
    workingWithDataBase: IWorkingWithDataBase,
    openDataMethod: IOpenDataMethod,
    deviceType: IDelegateMethodReturnString,
    deviceName: IDelegateMethodReturnString,
    downloadDirectoryPath: IDelegateMethodReturnFile
) {

    init {
        /**
         * Config custom classes
         */
        AppConfig.AlertInterface.errorIMessage = errorMessageConfig
        AppConfig.AlertInterface.iLoadAlert = loadAlertConfigClass as Class<ILoadAlert>
        AppConfig.AlertInterface.iMessage = messageConfig
        AppConfig.AlertInterface.littleIMessage = littleMessageConfig
        AppConfig.AlertInterface.messageForNotifyAboutCompleteDownloadProcess = notifyAboutCompleteProcessConfig
        AppConfig.SystemClipboard.iSystemClipboard = systemClipboardConfig
        AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase = workingWithDataBase
        AppConfig.OpenDataMethod.iOpenDataMethod = openDataMethod
        Thread {
            AppOption.DEVICE_TYPE = deviceType.stringMethod()
            AppOption.LOCAL_DEVICE_NAME = deviceName.stringMethod()
            AppOption.DIRECTORY_FOR_DOWNLOAD_FILES = downloadDirectoryPath.fileMethod()
        }.start()
        /**
         * Config default classes
         */
        AppConfig.Action.SendTypeInterface.iActionForSendType = ActionForSendType
        AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO = DeviceModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase)
        AppConfig.DataBase.ModelDAOInterface.iFileModelDAO = FileModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase)
        AppConfig.DataBase.ModelDAOInterface.iTrustedDeviceModelDAO = TrustedDeviceModelModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase)
        AppConfig.DataBase.ModelDAOInterface.iTransferredFilesHistoryModelDAO = TransferredFilesHistoryModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase)
        AppConfig.DataBase.ModelDAOInterface.iAcceptedFilesHistoryModelDAO = AcceptedFilesHistoryModelDAO(AppConfig.DataBase.WorkWithDataBaseInterface.iWorkingWithDataBase)
        AppConfig.IPWorkInterface.IPV4.iIP = PackageIPVersion4()
        AppConfig.WorkingWithClientInterface.iWorkingWithClient = StartForWorkingWithClient()
        AppConfig.ServerControllInterface.iServer = Server()
        /**
         * Start WDT Components
         */
        AppConfig.IPWorkInterface.IPV4.iIP.getListOfIP()
    }

    fun start() {
        AppConfig.ServerControllInterface.iServer.startServerSocket()
    }

}