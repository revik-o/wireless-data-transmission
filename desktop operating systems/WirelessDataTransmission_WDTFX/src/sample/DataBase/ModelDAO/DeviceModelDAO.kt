package sample.DataBase.ModelDAO

import sample.DataBase.FeedReaderContract
import sample.DataBase.IDataBase
import sample.DataBase.Model.DeviceModel

class DeviceModelDAO(iDataBase: IDataBase): IDAO {

    private val iDataBase: IDataBase = iDataBase
    private val deviceTable: FeedReaderContract.FeedDevice = FeedReaderContract.FeedDevice

    init {
        if (!iDataBase.createDataBase()) println("Database didn't create")
        if (!iDataBase.executeQuery(FeedReaderContract.SQL_CREATE_DEVICE)) println("Table in Database didn't create")
    }

    fun insert(deviceModel: DeviceModel) {
        if (deviceModel.ipAddress != null && deviceModel.deviceType != null && deviceModel.deviceName != null) {
            if (deviceModel.id != null)
                iDataBase.executeQuery("INSERT INTO ${deviceTable.TABLE_NAME} " +
                        "(${deviceTable.ID}, ${deviceTable.DEVICE_NAME}, ${deviceTable.DEVICE_TYPE}, ${deviceTable.IP_ADDRESS}) " +
                        "VALUES ('${deviceModel.id}', '${deviceModel.deviceName}', '${deviceModel.deviceType}', '${deviceModel.ipAddress}');")
            else iDataBase.executeQuery("INSERT INTO ${deviceTable.TABLE_NAME} " +
                    "(${deviceTable.DEVICE_NAME}, ${deviceTable.DEVICE_TYPE}, ${deviceTable.IP_ADDRESS}) " +
                    "VALUES ('${deviceModel.deviceName}', '${deviceModel.deviceType}', '${deviceModel.ipAddress}');")
        }
    }

    fun update(deviceModel: DeviceModel) {
        iDataBase.executeQuery("UPDATE ${deviceTable.TABLE_NAME} " +
                "SET ${deviceTable.DEVICE_NAME} = '${deviceModel.deviceName}', " +
                "${deviceTable.DEVICE_TYPE} = '${deviceModel.deviceType}', " +
                "${deviceTable.IP_ADDRESS} = '${deviceModel.ipAddress}' " +
                "WHERE ${deviceTable.ID} = '${deviceModel.id}';")
    }

    fun delete(deviceModel: DeviceModel) {
        iDataBase.executeQuery("DELETE FROM ${deviceTable.TABLE_NAME} WHERE ${deviceTable.ID} = '${deviceModel.id}';")
    }

    fun selectWhereIPLike(ip4Str: String): ArrayList<Array<String>> = iDataBase.executeRowQuery("SELECT * FROM ${deviceTable.TABLE_NAME} WHERE ${deviceTable.IP_ADDRESS} LIKE '$ip4Str%'")

    override fun deleteTable() {
        iDataBase.executeQuery(FeedReaderContract.SQL_DROP_DEVICE)
    }

    override fun selectAll(): ArrayList<Array<String>> = iDataBase.executeRowQuery("SELECT * FROM ${deviceTable.TABLE_NAME};")

    override fun selectAllWithRowId(): ArrayList<Array<String>> = iDataBase.executeRowQuery("SELECT keyid, ${deviceTable.ID}, ${deviceTable.DEVICE_NAME}, ${deviceTable.DEVICE_TYPE}, ${deviceTable.IP_ADDRESS} FROM ${deviceTable.TABLE_NAME};")

}