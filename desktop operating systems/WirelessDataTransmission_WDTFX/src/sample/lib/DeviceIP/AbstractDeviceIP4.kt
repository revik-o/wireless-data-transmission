package sample.lib.DeviceIP

import sample.lib.isNumeric
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration
import java.util.ArrayList

/**
 * This class realize IDeviceIP interface
 */
open abstract class AbstractDeviceIP4: IDeviceIP {

    override var listIP: ArrayList<String> = ArrayList()
        get() {
            val ipList: ArrayList<String> = ArrayList()
            val enumerationNetworkInterfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (enumerationNetworkInterfaces.hasMoreElements()) {
                val enumerationInetAddresses: Enumeration<InetAddress> = enumerationNetworkInterfaces.nextElement().inetAddresses
                while (enumerationInetAddresses.hasMoreElements()) {
                    val inetAdress: InetAddress = enumerationInetAddresses.nextElement()
                    val stringForFirst8BitFromIPAddress: String = inetAdress.hostAddress.takeWhile { it != '.' }
                    if (isNumeric(stringForFirst8BitFromIPAddress))
                        if (stringForFirst8BitFromIPAddress != "127")
                            ipList.add(inetAdress.hostAddress)
                }
            }
            return ipList
        }

    abstract fun enumerationIP(): ArrayList<String>

}