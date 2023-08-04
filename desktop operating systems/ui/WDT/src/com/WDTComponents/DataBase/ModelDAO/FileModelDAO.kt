package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.FeedReaderContract
import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.Model.FileModel

class FileModelDAO(iWorkingWithDataBase: IWorkingWithDataBase): IFileModelDAO, IDAO {

    private val iWorkingWithDataBase: IWorkingWithDataBase = iWorkingWithDataBase
    private val fileTable: FeedReaderContract.FeedFile = FeedReaderContract.FeedFile

    init {
        if (!iWorkingWithDataBase.createDataBase()) println("Database didn't create")
        if (!iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_CREATE_FILE)) println("Table \"${FeedReaderContract.FeedFile.TABLE_NAME}\" in Database didn't create")
    }

    private fun buildQueryForSelect(fileModel: FileModel): String = "SELECT * FROM ${fileTable.TABLE_NAME} " +
            "WHERE ${if (fileModel.id != null) "${fileTable.ID} = '${fileModel.id}' AND " else ""}" +
            "${fileTable.FILE_NAME} = '${fileModel.fileName}' AND " +
            "${fileTable.FILE_PATH} = '${fileModel.filePath}';"

    override fun addFile(fileModel: FileModel) {
        iWorkingWithDataBase.executeRowQuery(buildQueryForSelect(fileModel)).also {
            if (it.size == 0) {
                if (fileModel.id != null)
                    iWorkingWithDataBase.executeQuery("INSERT INTO ${fileTable.TABLE_NAME} " +
                            "(${fileTable.ID}, ${fileTable.FILE_NAME}, ${fileTable.FILE_PATH}, ${fileTable.FILE_SIZE}) " +
                            "VALUES ('${fileModel.id}', '${fileModel.fileName}', '${fileModel.filePath}', '${fileModel.fileSize}');")
                else
                    iWorkingWithDataBase.executeQuery("INSERT INTO ${fileTable.TABLE_NAME}" +
                            "(${fileTable.FILE_NAME}, ${fileTable.FILE_PATH}, ${fileTable.FILE_SIZE}) " +
                            "VALUES ('${fileModel.fileName}', '${fileModel.filePath}', '${fileModel.fileSize}');")
            }
            else if (it.size == 1)
                iWorkingWithDataBase.executeQuery(
                        "UPDATE ${fileTable.TABLE_NAME} " +
                                "SET ${fileTable.FILE_NAME} = '${fileModel.fileName}', " +
                                "${fileTable.FILE_PATH} = '${fileModel.filePath}', " +
                                "${fileTable.FILE_SIZE} = '${fileModel.fileSize}' " +
                                "WHERE ${fileTable.ID} = '${it[0][0].toInt()}'"
                )
        }
    }

    override fun addFileAsynchronously(fileModel: FileModel) {
        Thread { addFile(fileModel) }.start()
    }

    override fun getFile(fileModel: FileModel): FileModel {
        iWorkingWithDataBase.executeRowQuery(buildQueryForSelect(fileModel)).also {
            var tempFileModel = FileModel("", "", 0)
            if (it.size == 1) {
                val line: Array<String> = it[0]
                tempFileModel = FileModel(line[0].toInt(), line[1], line[2], line[3].toLong())
            }
            return tempFileModel
        }
    }

    override fun getFileId(fileModel: FileModel): Int {
        iWorkingWithDataBase.executeRowQuery(
                "SELECT ${fileTable.ID} FROM ${fileTable.TABLE_NAME} " +
                        "WHERE ${fileTable.FILE_NAME} = '${fileModel.fileName}' AND " +
                        "${fileTable.FILE_PATH} = '${fileModel.filePath}' AND " +
                        "${fileTable.FILE_SIZE} = '${fileModel.fileSize}';"
        ).also {
            return when (it.size) {
                0 -> 0
                1 -> it[0][0].toInt()
                else -> it[it.size - 1][0].toInt()
            }
        }
    }

    override fun deleteFile(fileModel: FileModel) {
        if (fileModel.id != null)
            iWorkingWithDataBase.executeQuery("DELETE FROM ${fileTable.TABLE_NAME} WHERE ${fileTable.ID} = '${fileModel.id}';")
    }

    override fun deleteTable() {
        iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_DROP_FILE)
    }

    override fun selectAll(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${fileTable.TABLE_NAME};")

//    override fun selectAllWithRowId(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT keyid, ${fileTable.ID}, ${fileTable.FILE_NAME}, ${fileTable.FILE_PATH}, ${fileTable.FILE_SIZE} FROM ${fileTable.TABLE_NAME};")

}