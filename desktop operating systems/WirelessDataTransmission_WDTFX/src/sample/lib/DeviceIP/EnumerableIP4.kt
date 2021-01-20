package sample.lib.DeviceIP

fun enumerableIp4(strIP: String): String {
    var tempString = ""
    var quantityDots = 0
    for (ch in strIP) {
        if (ch == '.') ++quantityDots
        else if (quantityDots == 3) break
        tempString += ch
    }
    return tempString
}