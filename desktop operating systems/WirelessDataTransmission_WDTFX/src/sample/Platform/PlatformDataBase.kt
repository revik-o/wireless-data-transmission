package sample.Platform

import sample.APPLICATION_NAME
import sample.DataBase.IWorkingWithDataBase
import java.io.File
import java.sql.Connection
import java.sql.Statement
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.ResultSet

val COMMON_DIRECTORY = File("common").absolutePath

class PlatformDataBase: IWorkingWithDataBase {

    private lateinit var connection: Connection
    private lateinit var statement: Statement

    override fun createDataBase(): Boolean {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:$COMMON_DIRECTORY/${APPLICATION_NAME}_Database.db")
            this.statement = this.connection.createStatement()
            return true
        }catch (E: SQLException) {
            E.printStackTrace()
        }
        return false
    }

    override fun executeQuery(SQLQuery: String): Boolean = try {
        statement.executeUpdate(SQLQuery)
        true
    } catch (E: Exception) {false}

    override fun executeRowQuery(SQLQuery: String): ArrayList<Array<String>> {
        val arrayList: ArrayList<Array<String>> = ArrayList()
        val resultSet: ResultSet = statement.executeQuery(SQLQuery)
        while (resultSet.next()) {
            val tempArrayString: Array<String?> = arrayOfNulls(resultSet.metaData.columnCount)
            for (i in 1 .. resultSet.metaData.columnCount)
                tempArrayString[i - 1] = resultSet.getString(i)
            arrayList.add(tempArrayString as Array<String>)
        }
        return arrayList
    }

    override fun closeConnection() {
        this.connection.close()
    }

}