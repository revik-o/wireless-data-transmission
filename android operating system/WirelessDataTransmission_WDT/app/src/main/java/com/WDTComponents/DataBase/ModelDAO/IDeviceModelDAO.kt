package com.WDTComponents.DataBase.ModelDAO

interface IDeviceModelDAO {

    fun addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress: String, nameDevice: String, typeDevice: String)
    fun selectWhereIPLike(ip4Str: String): ArrayList<Array<String>>

}