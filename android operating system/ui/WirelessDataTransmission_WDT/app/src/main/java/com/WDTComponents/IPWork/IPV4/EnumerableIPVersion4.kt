package com.WDTComponents.IPWork.IPV4

import com.WDTComponents.IPWork.IEnumerableIP

open class EnumerableIPVersion4: IPVersion4(), IEnumerableIP {

    protected var listEnumerableIP: ArrayList<String> = ArrayList()

    override fun getEnumerableListOfIP(): List<String> {
        this.listEnumerableIP = StaticFunctionsEnumerableIPVersion4.enumerableListOfIPVersion4(this.getListOfIP())
        return this.listEnumerableIP
    }

    override fun quicklyGetEnumerableListOfIP(): List<String> = this.listEnumerableIP

}