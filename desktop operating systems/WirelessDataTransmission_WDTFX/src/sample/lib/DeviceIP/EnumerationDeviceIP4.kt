package sample.lib.DeviceIP

/**
 * This class realize IEnumerationDeviceIp interface
 */
open class EnumerationDeviceIP4: AbstractDeviceIP4() {

    override fun enumerationIP(): ArrayList<String> {
        val listIP: ArrayList<String> = ArrayList()
        this.listIP.forEach {
            listIP.add(enumerableIp4(it))
        }
        return listIP
    }

}