package com.WDTlib.DataBaseInterface

interface IWorkingWithDataBase {

    fun createDataBase(): Boolean
    fun executeQuery(SQLQuery: String): Boolean
    fun executeRowQuery(SQLQuery: String): ArrayList<Array<String>>
    fun closeConnection()

}