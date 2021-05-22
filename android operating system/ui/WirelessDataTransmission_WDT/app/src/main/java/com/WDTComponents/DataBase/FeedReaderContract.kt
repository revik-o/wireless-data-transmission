package com.WDTComponents.DataBase

object FeedReaderContract {

    /**
     * !!! Device Table !!!
     * id: Long
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

    const val SQL_CREATE_DEVICE: String = "CREATE TABLE IF NOT EXISTS ${FeedDevice.TABLE_NAME} (" +
            "${FeedDevice.ID} LONG PRIMARY KEY AUTOINCREMENT, " +
            "${FeedDevice.DEVICE_NAME} TEXT NOT NULL, " +
            "${FeedDevice.DEVICE_TYPE} TEXT NOT NULL, " +
            "${FeedDevice.IP_ADDRESS} TEXT NOT NULL);"

    const val SQL_DROP_DEVICE: String = "DROP TABLE IF EXISTS ${FeedDevice.TABLE_NAME};"

    /**
     * !!! File Table !!!
     * id: Long
     * file_name: Text
     * file_path: Text
     * file_size: Long
     */
    object FeedFile {
        const val TABLE_NAME: String = "file"
        const val ID: String = "id"
        const val FILE_NAME: String = "file_name"
        const val FILE_PATH: String = "file_path"
        const val FILE_SIZE: String = "file_size"
    }

    const val SQL_CREATE_FILE: String = "CREATE TABLE IF NOT EXISTS ${FeedFile.TABLE_NAME} (" +
            "${FeedFile.ID} LONG PRIMARY KEY AUTOINCREMENT, " +
            "${FeedFile.FILE_NAME} TEXT NOT NULL, " +
            "${FeedFile.FILE_PATH} TEXT NOT NULL, " +
            "${FeedFile.FILE_SIZE} LONG NOT NULL);"

    const val SQL_DROP_FILE: String = "DROP TABLE IF EXISTS ${FeedFile.TABLE_NAME};"

    /**
     * !!! Accepted Files History Table !!!
     * id: Long
     * device_id: Text
     * file_id: Text
     * file_date: Date
     */
    object FeedAcceptedFilesHistory {
        const val TABLE_NAME: String = "accepted_files_history"
        const val ID: String = "id"
        const val DEVICE_ID: String = "device_id"
        const val FILE_ID: String = "file_id"
        const val FILE_DATE: String = "file_date"
    }

    const val SQL_CREATE_AcceptedFilesHistory: String = "CREATE TABLE IF NOT EXISTS ${FeedAcceptedFilesHistory.TABLE_NAME} (" +
            "${FeedAcceptedFilesHistory.ID} LONG PRIMARY KEY AUTOINCREMENT, " +
            "${FeedAcceptedFilesHistory.DEVICE_ID} LONG NOT NULL, " +
            "${FeedAcceptedFilesHistory.FILE_ID} LONG NOT NULL, " +
            "${FeedAcceptedFilesHistory.FILE_DATE} DATE NOT NULL, " +
            "FOREIGN KEY (${FeedAcceptedFilesHistory.DEVICE_ID}) REFERENCES ${FeedDevice.TABLE_NAME}(${FeedDevice.ID}), " +
            "FOREIGN KEY (${FeedAcceptedFilesHistory.FILE_ID}) REFERENCES ${FeedFile.TABLE_NAME}(${FeedFile.ID}));"

    const val SQL_DROP_AcceptedFilesHistory: String = "DROP TABLE IF EXISTS ${FeedAcceptedFilesHistory.TABLE_NAME};"

}