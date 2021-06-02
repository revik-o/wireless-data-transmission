package com.WDTComponents.DataBase.Model

import java.time.LocalDate

class TrustedDeviceModel {

    var id: Int? = null
        private set
    var device_id: Int? = null
        private set
    var title: String? = null
        private set
    var trust_date: String? = null
        private set

    constructor(id: Int) {
        this.id = id
    }

    constructor(device_id: Int, title: String) {
        this.device_id = device_id
        this.title = title
        this.trust_date = LocalDate.now().toString()
    }

    constructor(id: Int, device_id: Int, title: String) {
        this.id = id
        this.device_id = device_id
        this.title = title
        this.trust_date = LocalDate.now().toString()
    }

    constructor(device_id: Int, title: String, trust_date: String) {
        this.device_id = device_id
        this.title = title
        this.trust_date = trust_date
    }

    constructor(id: Int, device_id: Int, title: String, trust_date: String) {
        this.id = id
        this.device_id = device_id
        this.title = title
        this.trust_date = trust_date
    }

    override fun toString(): String = "{ id=$id, device_id=$device_id, title=$title, trust_date=$trust_date }"

}