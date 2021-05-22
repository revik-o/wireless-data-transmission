package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.Model.FileModel

interface IFileModelDAO {

    fun addFile(fileModel: FileModel)

    fun getFile(fileModel: FileModel): FileModel

}