package com.WDTComponents.DataBase.Model

class AcceptedFilesHistoryModel {

    var id: Int? = null
        private set
    var deviceId: Int? = null
        private set
    var fileId: Int? = null
        private set
    var dateOfAdoption: String? = null
        private set

    constructor(id: Int) {
        this.id = id
    }

    constructor(deviceId: Int, fileId: Int, dateOfAdoption: String) {
        this.deviceId = deviceId
        this.fileId = fileId
        this.dateOfAdoption = dateOfAdoption
    }

    constructor(id: Int, deviceId: Int, fileId: Int, dateOfAdoption: String) {
        this.id = id
        this.deviceId = deviceId
        this.fileId = fileId
        this.dateOfAdoption = dateOfAdoption
    }

    override fun toString(): String {
        return "{ id=$id, deviceId=$deviceId, fileId=$fileId, dateOfAdoption=$dateOfAdoption }"
    }

}