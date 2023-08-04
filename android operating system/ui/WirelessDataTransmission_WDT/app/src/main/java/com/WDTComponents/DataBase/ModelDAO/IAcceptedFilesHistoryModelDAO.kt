package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.Model.AcceptedFilesHistoryModel

interface IAcceptedFilesHistoryModelDAO {

    fun addNewAcceptedFileOrUpdate(acceptedFilesHistoryModel: AcceptedFilesHistoryModel)

    fun addNewAcceptedFileOrUpdateAsynchronously(acceptedFilesHistoryModel: AcceptedFilesHistoryModel)

    fun removeAcceptedFile(acceptedFilesHistoryModel: AcceptedFilesHistoryModel)

    fun clearAllAcceptedFiles()

}