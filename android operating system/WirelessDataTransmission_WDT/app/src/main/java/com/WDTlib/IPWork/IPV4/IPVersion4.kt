package com.WDTlib.IPWork.IPV4

import com.WDTlib.IPWork.IIP
import com.WDTlib.Other.isNumeric

import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration

open class IPVersion4: IIP {

    protected var listIP: ArrayList<String> = ArrayList()

    override fun getListOfIP(): List<String> {
        val tempListIP: ArrayList<String> = ArrayList()
        val enumerationNetworkInterfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
        while (enumerationNetworkInterfaces.hasMoreElements()) {
            val enumerationInetAddresses: Enumeration<InetAddress> = enumerationNetworkInterfaces.nextElement().inetAddresses
            while (enumerationInetAddresses.hasMoreElements()) {
                val inetAdress: InetAddress = enumerationInetAddresses.nextElement()
                val stringForFirst8BitFromIPAddress: String = inetAdress.hostAddress.takeWhile { it != '.' }
                if (isNumeric(stringForFirst8BitFromIPAddress))
                    if (stringForFirst8BitFromIPAddress != "127")
                        tempListIP.add(inetAdress.hostAddress)
            }
        }
        this.listIP = tempListIP
        return this.listIP
    }

    override fun quicklyGetListOfIP(): List<String> = this.listIP

}