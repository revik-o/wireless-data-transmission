package ua.edu.onaft.wirelessdatatransmission_wdt.Configuration

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.WDTComponents.DataBase.FeedReaderContract
import com.WDTComponents.DataBase.IWorkingWithDataBase
import ua.edu.onaft.wirelessdatatransmission_wdt.Common.StaticState
import java.util.*
import kotlin.collections.ArrayList

class WorkingWithDataBaseConfiguration: IWorkingWithDataBase, SQLiteOpenHelper(StaticState.activity.applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun closeConnection() {}

    override fun createDataBase(): Boolean { return true }

    override fun executeQuery(SQLQuery: String): Boolean = try {
        this.writableDatabase.execSQL(SQLQuery)
        true
    } catch (E: Exception) { false }

    @SuppressLint("Recycle")
    override fun executeRowQuery(SQLQuery: String): ArrayList<Array<String>> {
        val arrayList: ArrayList<Array<String>> = ArrayList()
        val resultSet: Cursor = this.writableDatabase.rawQuery(SQLQuery, null)
        while (resultSet.moveToNext()) {
            val tempArrayString: Array<String?> = arrayOfNulls(resultSet.columnCount)
            for (i in 1 .. resultSet.columnCount)
                tempArrayString[i - 1] = resultSet.getString(i-1)
            arrayList.add(tempArrayString as Array<String>)
        }
        return arrayList
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(FeedReaderContract.SQL_CREATE_DEVICE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(FeedReaderContract.SQL_DROP_DEVICE)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "WirelessDataTransmission (WDTFX)_Database.db"
    }

}