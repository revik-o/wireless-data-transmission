package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.Model.TransferredFilesHistoryModel

interface ITransferredFilesHistoryModelDAO {

    fun addNewTransferredFileOrUpdate(transferredFilesHistoryModel: TransferredFilesHistoryModel)

    fun addNewTransferredFileOrUpdateAsynchronously(transferredFilesHistoryModel: TransferredFilesHistoryModel)

    fun removeTransferredFile(transferredFilesHistoryModel: TransferredFilesHistoryModel)

    fun clearAllTransferredFiles(transferredFilesHistoryModel: TransferredFilesHistoryModel)

}