package com.WDTComponents.WorkingWithDevices

import com.WDTComponents.AppConfig
import com.WDTComponents.AppOption
import com.WDTComponents.DelegateMethods.IDelegateMethodIntegerArg
import com.WDTComponents.DelegateMethods.IDelegateMethodSocketAction
import com.WDTComponents.WorkingWithData.DataTransfer
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

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

//    constructor(timeOut: Int, actionMethodForFindDevice: IDelegateMethodSocketAction, actionMethodForTransferPercent: IDelegateMethodIntegerArg) {
//        this.timeOut = timeOut
//        this.initMethod(actionMethodForFindDevice, actionMethodForTransferPercent)
//    }

    override fun startScanDevices() {
        AppConfig.IPWorkInterface.IPV4.iIP.getEnumerableListOfIP().forEach {
            val lock = ReentrantLock()
            val condition = lock.newCondition()
            var isWorkingWithDB = true
            Thread {
                lock.withLock {
                    AppConfig.DataBase.ModelDAOInterface.iDeviceModelDAO.selectWhereIPLike(it).forEach { com.WDTComponents.WorkingWithData.DataTransfer.sendDataFromClient(InetSocketAddress(it[3], com.WDTComponents.AppOption.SOCKET_PORT), 0, actionMethodForFindDevice, localDeviceIPAndRepeatableIPsList) }
                    isWorkingWithDB = false
                    condition.signal()
                }
            }.start()
            Thread {
                lock.withLock {
                    if (isWorkingWithDB) condition.await()
                    val numberOfProcessorCores = Runtime.getRuntime().availableProcessors() * 2
                    var length = 255
                    var sum = 0.0
                    for (core in 0 until numberOfProcessorCores) {
                        val absoluteValueOfLength: Int =
                            length / (numberOfProcessorCores - (core - 1))
                        val endScanPoint: Int = length - absoluteValueOfLength
                        val startScanPoint: Int = endScanPoint - absoluteValueOfLength
                        length -= absoluteValueOfLength
                        Thread {
                            for (i in startScanPoint..endScanPoint) {
                                if (isEnumeration) {
                                    val temporaryIP: String = it + i
                                    if (InetAddress.getByName(temporaryIP).isReachable(timeOut)) {
                                        var checkIP = true
                                        for (loopIP in localDeviceIPAndRepeatableIPsList) {
                                            if (loopIP == temporaryIP) {
                                                checkIP = false
                                                break
                                            }
                                        }
                                        if (checkIP) {
                                            println("Find IP: $temporaryIP")
                                            DataTransfer.sendDataFromClient(
                                                InetSocketAddress(
                                                    temporaryIP,
                                                    AppOption.SOCKET_PORT
                                                ), 0, actionMethodForFindDevice
                                            )
                                        }
                                    }
                                    sum += i.toDouble() / 255
                                    actionMethodForTransferPercent.voidMethod((sum / 1.28f).toInt())
                                } else break
                            }
                            actionMethodForTransferPercent.voidMethod(100)
                        }.start()
                    }
                }
            }.start()
        }
    }

    override fun stopScanDevices() {
        this.isEnumeration = false
    }

}