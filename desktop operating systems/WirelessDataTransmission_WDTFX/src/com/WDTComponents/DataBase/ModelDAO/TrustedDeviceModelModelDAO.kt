package com.WDTComponents.DataBase.ModelDAO

import com.WDTComponents.DataBase.FeedReaderContract
import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.Model.TrustedDeviceModel

class TrustedDeviceModelModelDAO(iWorkingWithDataBase: IWorkingWithDataBase): ITrustedDeviceModelDAO, IDAO {

    private val iWorkingWithDataBase: IWorkingWithDataBase = iWorkingWithDataBase
    private val trustedDeviceTable: FeedReaderContract.FeedTrustedDevice = FeedReaderContract.FeedTrustedDevice

    init {
        if (!iWorkingWithDataBase.createDataBase()) println("Database didn't create")
        if (!iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_CREATE_TRUSTED_DEVICE)) println("Table \"${trustedDeviceTable.TABLE_NAME}\" in Database didn't create")
    }

    override fun addNewTrustedDevice(trustedDeviceModel: TrustedDeviceModel) {
        if (trustedDeviceModel.device_id != null) {
            if (trustedDeviceModel.id != null)
                iWorkingWithDataBase.executeQuery(
                        "INSERT INTO ${trustedDeviceTable.TABLE_NAME} " +
                                "(${trustedDeviceTable.ID}, ${trustedDeviceTable.DEVICE_ID}, ${trustedDeviceTable.TITLE}, ${trustedDeviceTable.TRUST_DATE}) " +
                                "VALUES ('${trustedDeviceModel.id}', '${trustedDeviceModel.device_id}', '${trustedDeviceModel.title}', '${trustedDeviceModel.trust_date}');"
                )
            else
                iWorkingWithDataBase.executeQuery(
                        "INSERT INTO ${trustedDeviceTable.TABLE_NAME} " +
                                "(${trustedDeviceTable.DEVICE_ID}, ${trustedDeviceTable.TITLE}, ${trustedDeviceTable.TRUST_DATE}) " +
                                "VALUES ('${trustedDeviceModel.device_id}', '${trustedDeviceModel.title}', '${trustedDeviceModel.trust_date}');"
                )
        }
    }

    override fun getTrustedDevice(trustedDeviceModel: TrustedDeviceModel): TrustedDeviceModel {
        if (trustedDeviceModel.device_id != null)
            iWorkingWithDataBase.executeRowQuery(
                    "SELECT * FROM ${trustedDeviceTable.TABLE_NAME} " +
                            "WHERE ${trustedDeviceTable.DEVICE_ID} = '${trustedDeviceModel.device_id}';"
            ).also {
                return if (it.size == 1) TrustedDeviceModel(it[0][0].toInt(), it[0][1].toInt(), it[0][2])
                else TrustedDeviceModel(0)
            }
        else return TrustedDeviceModel(0)
    }

    override fun updateInfoAboutTrustedDevice(trustedDeviceModel: TrustedDeviceModel) {
        if (trustedDeviceModel.id != null)
            iWorkingWithDataBase.executeQuery(
                    "UPDATE ${trustedDeviceTable.TABLE_NAME} " +
                            "SET ${trustedDeviceTable.DEVICE_ID} = '${trustedDeviceModel.device_id}', " +
                            "${trustedDeviceTable.TITLE} = '${trustedDeviceModel.title}', " +
                            "${trustedDeviceTable.TRUST_DATE} = '${trustedDeviceModel.trust_date}' " +
                            "WHERE ${trustedDeviceTable.ID} = ${trustedDeviceModel.id};"
            )
    }

    override fun removeTrustedDevice(trustedDeviceModel: TrustedDeviceModel) {
        if (trustedDeviceModel.id != null)
            iWorkingWithDataBase.executeQuery(
                    "DELETE FROM ${trustedDeviceTable.TABLE_NAME} WHERE ${trustedDeviceTable.ID} = '${trustedDeviceModel.id}';"
            )
    }



    override fun deleteTable() {
        iWorkingWithDataBase.executeQuery(FeedReaderContract.SQL_DROP_TRUSTED_DEVICE)
    }

    override fun selectAll(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT * FROM ${trustedDeviceTable.TABLE_NAME};")

//    override fun selectAllWithRowId(): ArrayList<Array<String>> = iWorkingWithDataBase.executeRowQuery("SELECT keyid, ${trustedDeviceTable.ID}, ${trustedDeviceTable.DEVICE_ID}, ${trustedDeviceTable.TITLE}, ${trustedDeviceTable.TRUST_DATE} FROM ${trustedDeviceTable.TABLE_NAME};")

}