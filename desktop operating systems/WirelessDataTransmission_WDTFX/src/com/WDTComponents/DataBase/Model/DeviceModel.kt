package com.WDTComponents.DataBase.Model

/**
 *
 */
class DeviceModel {

    var id : Int? = null
    var deviceName : String? = null
    var deviceType : String? = null
    var ipAddress : String? = null

    constructor()

    constructor(deviceName: String?, deviceType: String?, ipAddress: String?) {
        this.deviceName = deviceName
        this.deviceType = deviceType
        this.ipAddress = ipAddress
    }

    constructor(id: Int?, deviceName: String?, deviceType: String?, ipAddress: String?) {
        this.id = id
        this.deviceName = deviceName
        this.deviceType = deviceType
        this.ipAddress = ipAddress
    }

    override fun toString(): String = "{ id=$id, deviceName=$deviceName, deviceType=$deviceType, ipAddress=$ipAddress }"

}
