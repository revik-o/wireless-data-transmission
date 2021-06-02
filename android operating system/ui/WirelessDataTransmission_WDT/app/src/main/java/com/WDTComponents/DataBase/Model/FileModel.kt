package com.WDTComponents.DataBase.Model

class FileModel {

    var id : Int? = null
        private set
    var fileName : String? = null
        private set
    var filePath : String? = null
        private set
    var fileSize : Long? = null
        private set

    constructor(id: Int) {
        this.id = id
    }

    constructor(fileName: String, filePath: String, fileSize: Long) {
        this.fileName = fileName
        this.filePath = filePath
        this.fileSize = fileSize
    }

    constructor(id: Int, fileName: String, filePath: String, fileSize: Long) {
        this.id = id
        this.fileName = fileName
        this.filePath = filePath
        this.fileSize = fileSize
    }

    override fun toString(): String = "{ id=$id, fileName=$fileName, filePath=$filePath, fileSize=$fileSize }"

}