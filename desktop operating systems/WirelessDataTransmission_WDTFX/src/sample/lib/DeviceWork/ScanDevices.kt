package sample.lib.DeviceWork

import sample.lib.DelegateFunction.IDelegateFunction4Action
import sample.lib.DeviceIP.EnumerationDeviceIP4
import sample.lib.IDelegateFunctionInt
import sample.lib.SocketCommunication.SOCKET_PORT

import java.net.InetAddress
import java.net.InetSocketAddress

/**
 *
 */
class ScanDevices {

    private var isEnumeration: Boolean = true // It's variable like flag, if this variable equal false, then method "ScanDevices" stop scan
    private val deviceIP: EnumerationDeviceIP4 = EnumerationDeviceIP4() // This object needed for count IP
    var timeOut: Int = 500
        get set

    private lateinit var actionForFoundDevice: IDelegateFunction4Action
    private lateinit var actionForTransferPercent: IDelegateFunctionInt

    private fun initMethod(actionForFoundDevice: IDelegateFunction4Action, actionForTransferPercent: IDelegateFunctionInt) {
        this.actionForFoundDevice = actionForFoundDevice
        this.actionForTransferPercent = actionForTransferPercent
        this.scanDevices()
    }

    constructor(actionForFoundDevice: IDelegateFunction4Action, actionForTransferPercent: IDelegateFunctionInt) {
        initMethod(actionForFoundDevice, actionForTransferPercent)
    }

    constructor(timeOut: Int, actionForFoundDevice: IDelegateFunction4Action, actionForTransferPercent: IDelegateFunctionInt) {
        this.timeOut = timeOut
        initMethod(actionForFoundDevice, actionForTransferPercent)
    }

    private fun scanDevices() {
        deviceIP.enumerationIP().forEach {
            val numberOfProcessorCores = Runtime.getRuntime().availableProcessors()
            var length = 255
            var sum = 0.0
            for (core in 0 until numberOfProcessorCores) {
                val absoluteValue = length / (numberOfProcessorCores - (core - 1))
                val endScan = length - absoluteValue
                val startScan = endScan - absoluteValue
                length -= absoluteValue
                Thread {
                    for (i in startScan..endScan) {
                        if (isEnumeration) {
                            val IP = it + i
                            if (InetAddress.getByName(IP).isReachable(timeOut)) {
                                var checkIP = true
                                for (loopIP in deviceIP.listIP) {
                                    if (loopIP == IP) {
                                        checkIP = false
                                        break
                                    }
                                }
                                checkIP = true /////////////////////////////////// <____________________________
                                if (checkIP) {
                                    println("Find IP: $IP")
                                    sendData(InetSocketAddress(IP, SOCKET_PORT), actionForFoundDevice)
                                }
                            }
                            sum += i.toDouble() / 255
                            actionForTransferPercent.voidFunction((sum / 1.28).toInt())
                        } else break
                    }
                    actionForTransferPercent.voidFunction(100)
                }.start()
            }
        }
    }

    fun stopScan() { isEnumeration = false }

}