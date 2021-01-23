package sample.DataBase

interface IDataBase {

    fun createDataBase(): Boolean
    fun executeQuery(SQLQuery: String): Boolean
    fun executeRowQuery(SQLQuery: String): ArrayList<Array<String>>

}