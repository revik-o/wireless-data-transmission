package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.Model.DeviceModel

interface IDeviceModelDAO {

    fun addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress: String, nameDevice: String, typeDevice: String)

    fun selectWhereIPLike(ip4Str: String): ArrayList<Array<String>>

    fun getDeviceId(deviceModel: DeviceModel): Int

}