package sample.DataBase.Model

/**
 *
 */
class DeviceModel {

    var id : Long? = null
        get set
    var deviceName : String? = null
        get set
    var deviceType : String? = null
        get set
    var ipAddress : String? = null
        get set

    constructor()

    constructor(deviceName: String?, deviceType: String?, ipAddress: String?) {
        this.deviceName = deviceName
        this.deviceType = deviceType
        this.ipAddress = ipAddress
    }

    constructor(id: Long?, deviceName: String?, deviceType: String?, ipAddress: String?) {
        this.id = id
        this.deviceName = deviceName
        this.deviceType = deviceType
        this.ipAddress = ipAddress
    }

}
