package com.WDTComponents.IPWork.IPV4

object StaticFunctionsEnumerableIPVersion4 {

    fun enumerableIPVersion4(stringIP: String): String {
        var tempString = ""
        var quantityDots = 0
        for (ch in stringIP) {
            if (ch == '.') ++quantityDots
            else if (quantityDots == 3) break
            tempString += ch
        }
        return tempString
    }

    fun enumerableListOfIPVersion4(listIP: List<String>): ArrayList<String> {
        val tempListIP: ArrayList<String> = ArrayList()
        listIP.forEach { tempListIP.add(enumerableIPVersion4(it)) }
        return tempListIP
    }

}