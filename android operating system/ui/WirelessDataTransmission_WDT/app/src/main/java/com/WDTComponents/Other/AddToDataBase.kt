package com.WDTComponents.Other

import com.WDTComponents.AppConfig
import com.WDTComponents.DataBase.Model.AcceptedFilesHistoryModel
import com.WDTComponents.DataBase.Model.DeviceModel
import com.WDTComponents.DataBase.Model.FileModel
import com.WDTComponents.DataBase.Model.TransferredFilesHistoryModel
import java.text.SimpleDateFormat
import java.util.*

fun addToDataBaseTransferredFile(name: String, absolutePath: String, length: Long) {
    Thread {
        val fileModel = FileModel(name, absolutePath, length)
        AppConfig.DataBase.ModelDAOInterface.iFileModelDAO.addFile(fileModel)
        AppConfig.DataBase.ModelDAOInterface.iTransferredFilesHistoryModelDAO.addNewTransferredFileOrUpdateAsynchronously(
                TransferredFilesHistoryModel(
                        AppConfig.DataBase.ModelDAOInterface.iFileModelDAO.getFileId(fileModel),
                        SimpleDateFormat("yyyy-MM-dd").format(Date())
                )
        )
    }.start()
}

fun addToDataBaseAcceptedFile(
        nameFile: String,
        filePath: String,
        lengthFile: Long,
        deviceName: String,
        deviceType: String,
        ipAddress: String
) {
    Thread {
        val fileModel = FileModel(nameFile, filePath, lengthFile)
        AppConfig.DataBase.ModelDAOInterface.iFileModelDAO.addFile(fileModel)
        AppConfig.DataBase.ModelDAOInterface.iAcceptedFilesHistoryModelDAO.addNewAcceptedFileOrUpdateAsynchronously(
                AcceptedFilesHistoryModel(
                        AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.getDeviceId(
                                DeviceModel(deviceName, deviceType, ipAddress)
                        ),
                        AppConfig.DataBase.ModelDAOInterface.iFileModelDAO.getFileId(fileModel),
                        SimpleDateFormat("yyyy-MM-dd").format(Date())
                )
        )
    }.start()
}