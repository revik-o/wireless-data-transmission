package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.Model.FileModel

interface IFileModelDAO {

    fun addFile(fileModel: FileModel)

    fun addFileAsynchronously(fileModel: FileModel)

    fun getFile(fileModel: FileModel): FileModel

    fun getFileId(fileModel: FileModel): Int

    fun deleteFile(fileModel: FileModel)

}