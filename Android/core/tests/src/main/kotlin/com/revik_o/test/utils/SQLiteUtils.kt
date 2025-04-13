package com.revik_o.test.utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

object SQLiteUtils {
    const val DEFAULT_JDBC_URL = "jdbc:sqlite:wdt_test.db"

    fun getConnectionToDB(): Connection = DriverManager.getConnection(DEFAULT_JDBC_URL)

    fun executeQuery(sql: String): Boolean =
        getConnectionToDB().createStatement().use { statement ->
            statement.execute(sql)
        }

    fun executeSelectQuery(sql: String): ResultSet =
        getConnectionToDB().createStatement().use { statement ->
            statement.executeQuery(sql)
        }
}