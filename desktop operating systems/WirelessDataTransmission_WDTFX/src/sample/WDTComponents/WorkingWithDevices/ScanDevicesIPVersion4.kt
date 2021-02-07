package sample.WDTComponents.WorkingWithDevices

import sample.WDTComponents.AppConfig
import sample.WDTComponents.AppOption
import sample.WDTComponents.DelegateMethods.IDelegateMethodIntegerArg
import sample.WDTComponents.DelegateMethods.IDelegateMethodSocketAction
import sample.WDTComponents.WorkingWithData.DataTransfer
import java.net.InetAddress
import java.net.InetSocketAddress

/**
 *
 */
class ScanDevicesIPVersion4: IScanDevices {

    private var isEnumeration: Boolean = true
    private var timeOut: Int = 500
    private var localDeviceIPAndRepeatableIPsList: ArrayList<String> = AppConfig.IPWorkInterface.IPV4.iIP.quicklyGetListOfIP() as ArrayList<String>

    private lateinit var actionMethodForFindDevice: IDelegateMethodSocketAction
    private lateinit var actionMethodForTransferPercent: IDelegateMethodIntegerArg

    private fun initMethod(actionMethodForFindDevice: IDelegateMethodSocketAction, actionMethodForTransferPercent: IDelegateMethodIntegerArg) {
        this.actionMethodForFindDevice = actionMethodForFindDevice
        this.actionMethodForTransferPercent = actionMethodForTransferPercent
        this.startScanDevices()
    }

    constructor(actionMethodForFindDevice: IDelegateMethodSocketAction, actionMethodForTransferPercent: IDelegateMethodIntegerArg) {
        this.initMethod(actionMethodForFindDevice, actionMethodForTransferPercent)
    }

    constructor(timeOut: Int, actionMethodForFindDevice: IDelegateMethodSocketAction, actionMethodForTransferPercent: IDelegateMethodIntegerArg) {
        this.timeOut = timeOut
        this.initMethod(actionMethodForFindDevice, actionMethodForTransferPercent)
    }

    override fun startScanDevices() {
        AppConfig.IPWorkInterface.IPV4.iIP.getEnumerableListOfIP().forEach {
            AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.selectWhereIPLike(it).forEach { DataTransfer.sendDataFromClient(InetSocketAddress(it[3], AppOption.SOCKET_PORT), 0, actionMethodForFindDevice, localDeviceIPAndRepeatableIPsList) }
            val numberOfProcessorCores = Runtime.getRuntime().availableProcessors() * 2
            var length = 255
            var sum = 0.0
            for (core in 0 until numberOfProcessorCores) {
                val absoluteValueOfLength: Int = length / (numberOfProcessorCores - (core - 1))
                val endScanPoint: Int = length - absoluteValueOfLength
                val startScanPoint: Int = endScanPoint - absoluteValueOfLength
                length -= absoluteValueOfLength
                Thread{
                    for (i in startScanPoint..endScanPoint) {
                        if (this.isEnumeration) {
                            val temporaryIP: String = it + i
                            if (InetAddress.getByName(temporaryIP).isReachable(timeOut)) {
                                var checkIP = true
                                for (loopIP in localDeviceIPAndRepeatableIPsList) {
                                    if (loopIP == temporaryIP) {
                                        checkIP = false
                                        break
                                    }
                                }
                                checkIP = true /////////////////////////////////// <____________________________
                                if (checkIP) {
                                    println("Find IP: $temporaryIP")
                                    DataTransfer.sendDataFromClient(InetSocketAddress(temporaryIP, AppOption.SOCKET_PORT), 0, actionMethodForFindDevice)
                                }
                            }
                            sum += i.toDouble() / 255
                            actionMethodForTransferPercent.voidMethod((sum / 1.28f).toInt())
                        }
                        else break
                    }
                    actionMethodForTransferPercent.voidMethod(100)
                }.start()
            }
        }
    }

    override fun stopScanDevices() {
        this.isEnumeration = false
    }

}