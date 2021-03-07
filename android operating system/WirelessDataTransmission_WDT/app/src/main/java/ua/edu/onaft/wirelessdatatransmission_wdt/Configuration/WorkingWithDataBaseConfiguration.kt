package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import com.WDTlib.DataBaseInterface.IWorkingWithDataBase

class WorkingWithDataBaseConfiguration: IWorkingWithDataBase {
    override fun closeConnection() {
    }

    override fun createDataBase(): Boolean {
        return false
    }

    override fun executeQuery(SQLQuery: String): Boolean {
        return false
    }

    override fun executeRowQuery(SQLQuery: String): ArrayList<Array<String>> {
        return ArrayList()
    }
}