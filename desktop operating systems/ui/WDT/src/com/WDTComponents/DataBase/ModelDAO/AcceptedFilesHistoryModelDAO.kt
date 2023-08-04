package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.FeedReaderContract
import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.Model.AcceptedFilesHistoryModel

class AcceptedFilesHistoryModelDAO(iWorkingWithDataBase: IWorkingWithDataBase): IAcceptedFilesHistoryModelDAO, IDAO {

    private val iWorkingWithDataBase: IWorkingWithDataBase = iWorkingWithDataBase
    private val acceptedFilesHistoryTable: FeedReaderContract.FeedAcceptedFilesHistory = FeedReaderContract.FeedAcceptedFilesHistory

    init {
        if (!iWorkingWithDataBase.createDataBase()) println("Database didn't create")
        if (!iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_CREATE_ACCEPTED_FILES_HISTORY)) println("Table \"${acceptedFilesHistoryTable.TABLE_NAME}\" in Database didn't create")
    }

    private fun buildQueryForSelect(acceptedFilesHistoryModel: AcceptedFilesHistoryModel): String = "SELECT * FROM ${acceptedFilesHistoryTable.TABLE_NAME} " +
            "WHERE ${if (acceptedFilesHistoryModel.id != null) "${acceptedFilesHistoryTable.ID} = '${acceptedFilesHistoryModel.id}' AND " else ""}" +
            "${acceptedFilesHistoryTable.DEVICE_ID} = '${acceptedFilesHistoryModel.deviceId}' AND " +
            "${acceptedFilesHistoryTable.FILE_ID} = '${acceptedFilesHistoryModel.fileId}';"

    override fun addNewAcceptedFileOrUpdate(acceptedFilesHistoryModel: AcceptedFilesHistoryModel) {
        if (acceptedFilesHistoryModel.deviceId != null && acceptedFilesHistoryModel.fileId != null && acceptedFilesHistoryModel.dateOfAdoption != null)
            iWorkingWithDataBase.executeRowQuery(buildQueryForSelect(acceptedFilesHistoryModel)).also {
                if (it.size == 0) {
                    if (acceptedFilesHistoryModel.id != null)
                        iWorkingWithDataBase.executeQuery(
                                "INSERT INTO ${acceptedFilesHistoryTable.TABLE_NAME} " +
                                        "(${acceptedFilesHistoryTable.ID}, ${acceptedFilesHistoryTable.DEVICE_ID}, ${acceptedFilesHistoryTable.FILE_ID}, ${acceptedFilesHistoryTable.DATE_OF_ADOPTION}) " +
                                        "VALUES ('${acceptedFilesHistoryModel.id}', '${acceptedFilesHistoryModel.deviceId}', '${acceptedFilesHistoryModel.fileId}', '${acceptedFilesHistoryModel.dateOfAdoption}');"
                        )
                    else
                        iWorkingWithDataBase.executeQuery(
                                "INSERT INTO ${acceptedFilesHistoryTable.TABLE_NAME} " +
                                        "(${acceptedFilesHistoryTable.DEVICE_ID}, ${acceptedFilesHistoryTable.FILE_ID}, ${acceptedFilesHistoryTable.DATE_OF_ADOPTION}) " +
                                        "VALUES ('${acceptedFilesHistoryModel.deviceId}', '${acceptedFilesHistoryModel.fileId}', '${acceptedFilesHistoryModel.dateOfAdoption}');"
                        )
                }
                else if (it.size == 1) {
                    iWorkingWithDataBase.executeQuery(
                            "UPDATE ${acceptedFilesHistoryTable.TABLE_NAME} " +
                                    "SET ${acceptedFilesHistoryTable.DEVICE_ID} = '${acceptedFilesHistoryModel.deviceId}', " +
                                    "${acceptedFilesHistoryTable.FILE_ID} = '${acceptedFilesHistoryModel.fileId}', " +
                                    "${acceptedFilesHistoryTable.DATE_OF_ADOPTION} = '${acceptedFilesHistoryModel.dateOfAdoption}' " +
                                    "WHERE ${acceptedFilesHistoryTable.ID} = '${it[0][0]}';"
                    )
                }
            }
    }

    override fun addNewAcceptedFileOrUpdateAsynchronously(acceptedFilesHistoryModel: AcceptedFilesHistoryModel) {
        Thread { addNewAcceptedFileOrUpdate(acceptedFilesHistoryModel) }.start()
    }

    override fun removeAcceptedFile(acceptedFilesHistoryModel: AcceptedFilesHistoryModel) {
        if (acceptedFilesHistoryModel.id != null)
            iWorkingWithDataBase.executeQuery("DELETE FROM ${acceptedFilesHistoryTable.TABLE_NAME} WHERE ${acceptedFilesHistoryTable.ID} = ${acceptedFilesHistoryModel.id};")
    }

    override fun clearAllAcceptedFiles() {
        iWorkingWithDataBase.executeQuery("DELETE FROM ${acceptedFilesHistoryTable.TABLE_NAME};")
    }

    override fun deleteTable() {
        iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_DROP_ACCEPTED_FILES_HISTORY)
    }

    override fun selectAll(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${acceptedFilesHistoryTable.TABLE_NAME};")

}