package sample.Platform.DataBase

import sample.DataBase.ModelDAO.DeviceModelDAO

private val platformDataBase: PlatformDataBase = PlatformDataBase()

var deviceModelDAO: DeviceModelDAO = DeviceModelDAO(platformDataBase)
    get
    private set