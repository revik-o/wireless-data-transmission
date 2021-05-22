package com.WDTComponents.DataBase.Model

class FileModel {

    var id : Long? = null
        private set
    var fileName : String? = null
        private set
    var filePath : String? = null
        private set
    var fileSize : Long? = null
        private set

    constructor(fileName: String, filePath: String, fileSize: Long) {
        this.fileName = fileName
        this.filePath = filePath
        this.fileSize = fileSize
    }

    constructor(id: Long, fileName: String, filePath: String, fileSize: Long) {
        this.id = id
        this.fileName = fileName
        this.filePath = filePath
        this.fileSize = fileSize
    }

}