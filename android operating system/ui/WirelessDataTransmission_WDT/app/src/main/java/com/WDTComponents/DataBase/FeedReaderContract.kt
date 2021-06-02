package com.WDTComponents.DataBase

object FeedReaderContract {

    /**
     * !!! Device Table !!!
     * id: Integer
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
            "${FeedDevice.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${FeedDevice.DEVICE_NAME} TEXT NOT NULL, " +
            "${FeedDevice.DEVICE_TYPE} TEXT NOT NULL, " +
            "${FeedDevice.IP_ADDRESS} TEXT NOT NULL);"

    const val SQL_DROP_DEVICE: String = "DROP TABLE IF EXISTS ${FeedDevice.TABLE_NAME};"

    /**
     * !!! File Table !!!
     * id: Integer
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
            "${FeedFile.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${FeedFile.FILE_NAME} TEXT NOT NULL, " +
            "${FeedFile.FILE_PATH} TEXT NOT NULL, " +
            "${FeedFile.FILE_SIZE} LONG NOT NULL);"

    const val SQL_DROP_FILE: String = "DROP TABLE IF EXISTS ${FeedFile.TABLE_NAME};"

    /**
     * !!! Trusted Device Table !!!
     * id: Integer
     * device_id: Integer
     * title: Text
     * trust_date: Text
     */
    object FeedTrustedDevice {
        const val TABLE_NAME: String = "trusted_device"
        const val ID: String = "id"
        const val DEVICE_ID: String = "device_id"
        const val TITLE: String = "title"
        const val TRUST_DATE: String = "trust_date"
    }

    const val SQL_CREATE_TRUSTED_DEVICE: String = "CREATE TABLE IF NOT EXISTS ${FeedTrustedDevice.TABLE_NAME} (" +
            "${FeedTrustedDevice.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${FeedTrustedDevice.DEVICE_ID} INTEGER NOT NULL, " +
            "${FeedTrustedDevice.TITLE} TEXT, " +
            "${FeedTrustedDevice.TRUST_DATE} TEXT NOT NULL, " +
            "FOREIGN KEY (${FeedTrustedDevice.DEVICE_ID}) REFERENCES ${FeedDevice.TABLE_NAME}(${FeedDevice.ID}));"

    const val SQL_DROP_TRUSTED_DEVICE: String = "DROP TABLE IF EXISTS ${FeedTrustedDevice.TABLE_NAME};"

    /**
     * !!! Transferred Files History Table !!!
     * id: Integer
     * file_id: Integer
     * transmission_date: Text
     */
    object FeedTransferredFilesHistory {
        const val TABLE_NAME: String = "transferred_files_history"
        const val ID: String = "id"
        const val FILE_ID: String = "file_id"
        const val TRANSMISSION_DATE: String = "transmission_date"
    }

    const val SQL_CREATE_TRANSFERRED_FILES_HISTORY: String ="CREATE TABLE IF NOT EXISTS ${FeedTransferredFilesHistory.TABLE_NAME} (" +
            "${FeedTransferredFilesHistory.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${FeedTransferredFilesHistory.FILE_ID} INTEGER NOT NULL, " +
            "${FeedTransferredFilesHistory.TRANSMISSION_DATE} TEXT NOT NULL, " +
            "FOREIGN KEY (${FeedTransferredFilesHistory.FILE_ID}) REFERENCES ${FeedFile.TABLE_NAME}(${FeedFile.ID}));"

    const val SQL_DROP_TRANSFERRED_FILES_HISTORY: String = "DROP TABLE IF EXISTS ${FeedTransferredFilesHistory.TABLE_NAME};"

    /**
     * !!! Accepted Files History Table !!!
     * id: Integer
     * device_id: Integer
     * file_id: Integer
     * date_of_adoption: Text
     */
    object FeedAcceptedFilesHistory {
        const val TABLE_NAME: String = "accepted_files_history"
        const val ID: String = "id"
        const val DEVICE_ID: String = "device_id"
        const val FILE_ID: String = "file_id"
        const val DATE_OF_ADOPTION: String = "date_of_adoption"
    }

    const val SQL_CREATE_ACCEPTED_FILES_HISTORY: String = "CREATE TABLE IF NOT EXISTS ${FeedAcceptedFilesHistory.TABLE_NAME} (" +
            "${FeedAcceptedFilesHistory.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${FeedAcceptedFilesHistory.DEVICE_ID} INTEGER NOT NULL, " +
            "${FeedAcceptedFilesHistory.FILE_ID} INTEGER NOT NULL, " +
            "${FeedAcceptedFilesHistory.DATE_OF_ADOPTION} TEXT NOT NULL, " +
            "FOREIGN KEY (${FeedAcceptedFilesHistory.DEVICE_ID}) REFERENCES ${FeedDevice.TABLE_NAME}(${FeedDevice.ID}), " +
            "FOREIGN KEY (${FeedAcceptedFilesHistory.FILE_ID}) REFERENCES ${FeedFile.TABLE_NAME}(${FeedFile.ID}));"

    const val SQL_DROP_ACCEPTED_FILES_HISTORY: String = "DROP TABLE IF EXISTS ${FeedAcceptedFilesHistory.TABLE_NAME};"

}