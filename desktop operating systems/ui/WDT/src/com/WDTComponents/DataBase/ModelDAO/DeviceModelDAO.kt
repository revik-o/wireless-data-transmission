package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.FeedReaderContract
import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.Model.DeviceModel

import com.WDTComponents.IPWork.IPV4.StaticFunctionsEnumerableIPVersion4

class DeviceModelDAO(iWorkingWithDataBase: IWorkingWithDataBase): IDAO, IDeviceModelDAO {

    private val iWorkingWithDataBase: IWorkingWithDataBase = iWorkingWithDataBase
    private val deviceTable: FeedReaderContract.FeedDevice = FeedReaderContract.FeedDevice

    init {
        if (!iWorkingWithDataBase.createDataBase()) println("Database didn't create")
        if (!iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_CREATE_DEVICE)) println("Table \"${FeedReaderContract.FeedDevice.TABLE_NAME}\" in Database didn't create")
    }

    fun insert(deviceModel: DeviceModel) {
        if (deviceModel.ipAddress != null && deviceModel.deviceType != null && deviceModel.deviceName != null) {
            if (deviceModel.id != null)
                iWorkingWithDataBase.executeQuery("INSERT INTO ${deviceTable.TABLE_NAME} " +
                        "(${deviceTable.ID}, ${deviceTable.DEVICE_NAME}, ${deviceTable.DEVICE_TYPE}, ${deviceTable.IP_ADDRESS}) " +
                        "VALUES ('${deviceModel.id}', '${deviceModel.deviceName}', '${deviceModel.deviceType}', '${deviceModel.ipAddress}');")
            else iWorkingWithDataBase.executeQuery("INSERT INTO ${deviceTable.TABLE_NAME} " +
                    "(${deviceTable.DEVICE_NAME}, ${deviceTable.DEVICE_TYPE}, ${deviceTable.IP_ADDRESS}) " +
                    "VALUES ('${deviceModel.deviceName}', '${deviceModel.deviceType}', '${deviceModel.ipAddress}');")
        }
    }

    fun update(deviceModel: DeviceModel) {
        iWorkingWithDataBase.executeQuery("UPDATE ${deviceTable.TABLE_NAME} " +
                "SET ${deviceTable.DEVICE_NAME} = '${deviceModel.deviceName}', " +
                "${deviceTable.DEVICE_TYPE} = '${deviceModel.deviceType}', " +
                "${deviceTable.IP_ADDRESS} = '${deviceModel.ipAddress}' " +
                "WHERE ${deviceTable.ID} = '${deviceModel.id}';")
    }

    fun delete(deviceModel: DeviceModel) {
        iWorkingWithDataBase.executeQuery("DELETE FROM ${deviceTable.TABLE_NAME} WHERE ${deviceTable.ID} = '${deviceModel.id}';")
    }

    override fun selectWhereIPLike(ip4Str: String): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${deviceTable.TABLE_NAME} WHERE ${deviceTable.IP_ADDRESS} LIKE '$ip4Str%'")

    override fun addNewDeviceToDatabaseWithUsingFilter(internetProtocolAddress: String, nameDevice: String, typeDevice: String) {
        var isIpExistsInDatabase = false
        try {
            for (strings in this.selectWhereIPLike(StaticFunctionsEnumerableIPVersion4.enumerableIPVersion4(internetProtocolAddress)))
                if (strings[3].equals(internetProtocolAddress)) {
                    isIpExistsInDatabase = true
                    break
                }
        } catch (E: Exception) {
            isIpExistsInDatabase = true
        }
        if (!isIpExistsInDatabase) {
            this.insert(DeviceModel(nameDevice, typeDevice, internetProtocolAddress))
            println("add new device to database")
        }
    }

    private fun buildQueryForSelect(deviceModel: DeviceModel): String = "SELECT ${deviceTable.ID} FROM ${deviceTable.TABLE_NAME} " +
            "WHERE ${if (deviceModel.id != null) "${deviceTable.ID} = '${deviceModel.id}' AND " else ""}" +
            "${deviceTable.DEVICE_NAME} = '${deviceModel.deviceName}' AND " +
            "${deviceTable.DEVICE_TYPE} = '${deviceModel.deviceType}' AND " +
            "${deviceTable.IP_ADDRESS} = '${deviceModel.ipAddress}';"

    override fun getDeviceId(deviceModel: DeviceModel): Int {
        iWorkingWithDataBase.executeRowQuery(buildQueryForSelect(deviceModel)).also {
            return when (it.size) {
                0 -> 0
                1 -> it[0][0].toInt()
                else -> it[it.size - 1][0].toInt()
            }
        }
    }

    override fun deleteTable() {
        iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_DROP_DEVICE)
    }

    override fun selectAll(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${deviceTable.TABLE_NAME};")

//    override fun selectAllWithRowId(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT keyid, ${deviceTable.ID}, ${deviceTable.DEVICE_NAME}, ${deviceTable.DEVICE_TYPE}, ${deviceTable.IP_ADDRESS} FROM ${deviceTable.TABLE_NAME};")

}