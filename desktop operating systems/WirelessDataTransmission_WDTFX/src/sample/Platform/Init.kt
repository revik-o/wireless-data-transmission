package sample.Platform

import sample.Platform.DataBase.deviceModelDAO
import sample.lib.publicDeviceModelDAO
import sample.lib.publicILoadStageMessage
import sample.lib.publicIMessage4ServerSocket

fun InitApp() {
    publicIMessage4ServerSocket = AlertMessage()
    publicDeviceModelDAO = deviceModelDAO
    publicILoadStageMessage = LoadStageMessage().javaClass
}