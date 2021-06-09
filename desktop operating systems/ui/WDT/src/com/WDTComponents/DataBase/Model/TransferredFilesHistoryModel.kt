package com.WDTComponents.DataBase.Model

class TransferredFilesHistoryModel {

    var id: Int? = null
        private set
    var fileId: Int? = null
        private set
    var transmissionDate: String? = null
        private set

    constructor(id: Int)  {
        this.id = id
    }

    constructor(fileId: Int, transmissionDate: String) {
        this.fileId = fileId
        this.transmissionDate = transmissionDate
    }

    constructor(id: Int, fileId: Int, transmissionDate: String) {
        this.id = id
        this.fileId = fileId
        this.transmissionDate = transmissionDate
    }

}