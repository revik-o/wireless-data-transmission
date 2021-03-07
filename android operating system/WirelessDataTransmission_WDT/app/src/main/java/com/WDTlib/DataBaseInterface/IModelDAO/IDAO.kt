package com.WDTlib.DataBaseInterface.IModelDAO

interface IDAO {

    fun deleteTable()
    fun selectAll(): ArrayList<Array<String>>
    fun selectAllWithRowId(): ArrayList<Array<String>>

}