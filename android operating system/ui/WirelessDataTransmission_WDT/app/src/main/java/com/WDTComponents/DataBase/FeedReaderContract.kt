package com.WDTComponents.DataBase

object FeedReaderContract {

    /**
     * !!! Device Table !!!
     * id: Int
     * device_name: Text
     * device_type: Text
     * ip_address: Text
     */
    object FeedDevice {
        const val TABLE_NAME: String = "device"
        const val ID: String = "id"
        const val DEVICE_NAME: String = "device_name"
        const val DEVICE_TYPE: String = "device_type"
        const val IP_ADDRESS: String = "ip_address"
    }

    const val SQL_CREATE_DEVICE = "CREATE TABLE IF NOT EXISTS ${FeedDevice.TABLE_NAME} (" +
            "${FeedDevice.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${FeedDevice.DEVICE_NAME} TEXT NOT NULL, " +
            "${FeedDevice.DEVICE_TYPE} TEXT NOT NULL, " +
            "${FeedDevice.IP_ADDRESS} TEXT NOT NULL);"

    const val SQL_DROP_DEVICE = "DROP TABLE IF EXISTS ${FeedDevice.TABLE_NAME};"

    /**
     * !!! History .... !!!
     */
    object HistoryOfData {
        // Some Code
    }

}