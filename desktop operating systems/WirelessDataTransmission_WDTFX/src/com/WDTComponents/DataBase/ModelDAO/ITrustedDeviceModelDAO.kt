package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.Model.TrustedDeviceModel

interface ITrustedDeviceModelDAO {

    fun addNewTrustedDevice(trustedDeviceModel: TrustedDeviceModel)

    fun getTrustedDevice(trustedDeviceModel: TrustedDeviceModel): TrustedDeviceModel

    fun updateInfoAboutTrustedDevice(trustedDeviceModel: TrustedDeviceModel)

    fun removeTrustedDevice(trustedDeviceModel: TrustedDeviceModel)

}