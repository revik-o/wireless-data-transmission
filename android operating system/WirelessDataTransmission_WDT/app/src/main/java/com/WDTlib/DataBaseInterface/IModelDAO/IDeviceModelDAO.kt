package com.WDTlib.DataBaseInterface.IModelDAO

interface IDeviceModelDAO: IDAO {

    fun addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress: String, nameDevice: String, typeDevice: String)
    fun selectWhereIPLike(ip4Str: String): ArrayList<Array<String>>

}