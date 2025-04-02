package com.revik_o.core.dao

interface DaoI<T> {

    suspend fun getById(id: Long): T

    suspend fun getByIds(vararg ids: Long): Array<T>

    suspend fun getAll(): Array<T>

    suspend fun save(entity: T): T

    suspend fun saveAll(vararg entities: T): Array<T>

    suspend fun saveAll(entities: List<T>): List<T>

    suspend fun deleteById(id: Long): T

    suspend fun deleteByIds(vararg ids: Long): Array<T>

    suspend fun deleteAll()
}