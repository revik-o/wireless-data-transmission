package ua.edu.onaft.wirelessdatatransmission_wdt.Model

import com.WDTlib.DataBaseInterface.IModelDAO.IDeviceModelDAO
import com.WDTlib.DataBaseInterface.IWorkingWithDataBase

class DeviceModelDAO(iWorkingWithDataBase: IWorkingWithDataBase): IDeviceModelDAO {

    val iWorkingWithDataBase = iWorkingWithDataBase

    override fun addNewDeviceToDatabaseWithUsingFilter(
        internetProtocolAddress: String,
        nameDevice: String,
        typeDevice: String
    ) {
    }

    override fun deleteTable() {
    }

    override fun selectAll(): ArrayList<Array<String>> {
        return ArrayList()
    }

    override fun selectAllWithRowId(): ArrayList<Array<String>> {
        return ArrayList()
    }

    override fun selectWhereIPLike(ip4Str: String): ArrayList<Array<String>> {
        return ArrayList()
    }
}