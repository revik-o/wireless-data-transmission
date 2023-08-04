package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.FeedReaderContract
import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.Model.TransferredFilesHistoryModel

class TransferredFilesHistoryModelDAO(iWorkingWithDataBase: IWorkingWithDataBase): ITransferredFilesHistoryModelDAO, IDAO {

    private val iWorkingWithDataBase: IWorkingWithDataBase = iWorkingWithDataBase
    private val transferredFilesHistoryTable: FeedReaderContract.FeedTransferredFilesHistory = FeedReaderContract.FeedTransferredFilesHistory

    init {
        if (!iWorkingWithDataBase.createDataBase()) println("Database didn't create")
        if (!iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_CREATE_TRANSFERRED_FILES_HISTORY)) println("Table \"${transferredFilesHistoryTable.TABLE_NAME}\" in Database didn't create")
    }

    private fun buildQueryForSelect(transferredFilesHistoryModel: TransferredFilesHistoryModel): String = "SELECT * FROM ${transferredFilesHistoryTable.TABLE_NAME} " +
            "WHERE ${if (transferredFilesHistoryModel.id != null) "${transferredFilesHistoryTable.ID} = '${transferredFilesHistoryModel.id}' AND " else ""}" +
            "${transferredFilesHistoryTable.FILE_ID} = '${transferredFilesHistoryModel.fileId}';"

    override fun addNewTransferredFileOrUpdate(transferredFilesHistoryModel: TransferredFilesHistoryModel) {
        if (transferredFilesHistoryModel.fileId != null && transferredFilesHistoryModel.transmissionDate != null)
            iWorkingWithDataBase.executeRowQuery(buildQueryForSelect(transferredFilesHistoryModel)).also {
                if (it.size == 0) {
                    if (transferredFilesHistoryModel.id != null)
                        iWorkingWithDataBase.executeQuery(
                                "INSERT INTO ${transferredFilesHistoryTable.TABLE_NAME} " +
                                        "(${transferredFilesHistoryTable.ID}, ${transferredFilesHistoryTable.FILE_ID}, ${transferredFilesHistoryTable.TRANSMISSION_DATE}) " +
                                        "VALUES ('${transferredFilesHistoryModel.id}', '${transferredFilesHistoryModel.fileId}', '${transferredFilesHistoryModel.transmissionDate}');"
                        )
                    else
                        iWorkingWithDataBase.executeQuery(
                                "INSERT INTO ${transferredFilesHistoryTable.TABLE_NAME} " +
                                        "(${transferredFilesHistoryTable.FILE_ID}, ${transferredFilesHistoryTable.TRANSMISSION_DATE}) " +
                                        "VALUES ('${transferredFilesHistoryModel.fileId}', '${transferredFilesHistoryModel.transmissionDate}');"
                        )
                }
                else if (it.size == 1 && transferredFilesHistoryModel.id != null) {
                    iWorkingWithDataBase.executeQuery(
                            "UPDATE ${transferredFilesHistoryTable.TABLE_NAME} " +
                                    "SET ${transferredFilesHistoryTable.FILE_ID} = '${transferredFilesHistoryModel.fileId}', " +
                                    "${transferredFilesHistoryTable.TRANSMISSION_DATE} = '${transferredFilesHistoryModel.transmissionDate}', " +
                                    "WHERE ${transferredFilesHistoryTable.ID} = '${transferredFilesHistoryModel.id}';"
                    )
                }
            }
    }

    override fun addNewTransferredFileOrUpdateAsynchronously(transferredFilesHistoryModel: TransferredFilesHistoryModel) {
        Thread { addNewTransferredFileOrUpdate(transferredFilesHistoryModel) }.start()
    }

    override fun removeTransferredFile(transferredFilesHistoryModel: TransferredFilesHistoryModel) {
        if (transferredFilesHistoryModel.id != null)
            iWorkingWithDataBase.executeQuery(
                    "DELETE FROM ${transferredFilesHistoryTable.TABLE_NAME} WHERE ${transferredFilesHistoryTable.ID} = '${transferredFilesHistoryModel.id}';"
            )
    }

    override fun clearAllTransferredFiles(transferredFilesHistoryModel: TransferredFilesHistoryModel) {
        iWorkingWithDataBase.executeQuery("DELETE FROM ${transferredFilesHistoryTable.TABLE_NAME};")
    }

    override fun deleteTable() {
        iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_DROP_TRANSFERRED_FILES_HISTORY)
    }

    override fun selectAll(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${transferredFilesHistoryTable.TABLE_NAME};")

}