package sample.lib

import sample.DataBase.ModelDAO.DeviceModelDAO
import sample.lib.Message.ILoadStageMessage
import sample.lib.Message.IMessage

lateinit var publicIMessage4ServerSocket: IMessage
lateinit var publicDeviceModelDAO: DeviceModelDAO
lateinit var publicILoadStageMessage: Class<ILoadStageMessage>