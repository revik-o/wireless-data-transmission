package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.FeedReaderContract
import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.Model.FileModel

class FileModelDAO(iWorkingWithDataBase: IWorkingWithDataBase): IFileModelDAO, IDAO {

    private val iWorkingWithDataBase: IWorkingWithDataBase = iWorkingWithDataBase
    private val fileTable: FeedReaderContract.FeedFile = FeedReaderContract.FeedFile

    init {
        if (!iWorkingWithDataBase.createDataBase()) println("Database didn't create")
        if (!iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_CREATE_DEVICE)) println("Table in Database didn't create")
    }

    override fun addFile(fileModel: FileModel) {
        // TODO 
        Thread {
            iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${fileTable.TABLE_NAME} WHERE ${fileTable.FILE_NAME} ")
        }.start()
        if (fileModel.id != null)
            iWorkingWithDataBase.executeQuery("INSERT INTO ${fileTable.TABLE_NAME}" +
                    "(${fileTable.ID}, ${fileTable.FILE_NAME}, ${fileTable.FILE_PATH}, ${fileTable.FILE_SIZE}) " +
                    "VALUES ('${fileModel.id}', '${fileModel.fileName}', '${fileModel.filePath}', '${fileModel.fileSize}');")
        else
            iWorkingWithDataBase.executeQuery("INSERT INTO ${fileTable.TABLE_NAME}" +
                    "(${fileTable.FILE_NAME}, ${fileTable.FILE_PATH}, ${fileTable.FILE_SIZE}) " +
                    "VALUES ('${fileModel.fileName}', '${fileModel.filePath}', '${fileModel.fileSize}');")
    }

    override fun getFile(fileModel: FileModel): FileModel {
        iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${fileTable.TABLE_NAME} WHERE ")
        return FileModel("", "", 0)
    }

    override fun deleteTable() {
        iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_DROP_FILE)
    }

    override fun selectAll(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${fileTable.TABLE_NAME};")

    override fun selectAllWithRowId(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT keyid, ${fileTable.ID}, ${fileTable.FILE_NAME}, ${fileTable.FILE_PATH}, ${fileTable.FILE_SIZE} FROM ${fileTable.TABLE_NAME};")

}