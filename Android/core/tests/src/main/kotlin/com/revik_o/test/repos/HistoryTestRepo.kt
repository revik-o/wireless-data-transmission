package com.revik_o.test.repos

import com.revik_o.core.data.model.HistoryModel
import com.revik_o.core.data.repository.HistoryRepositoryI

class HistoryTestRepo: HistoryRepositoryI {
    override suspend fun getById(id: Long): HistoryModel {
        TODO("Not yet implemented")
    }

    override suspend fun getByIds(vararg ids: Long): Array<HistoryModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Array<HistoryModel> {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: HistoryModel): HistoryModel {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(vararg entities: HistoryModel): Array<HistoryModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(entities: List<HistoryModel>): List<HistoryModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Long): HistoryModel {
        TODO("Not yet implemented")
    }

    override suspend fun deleteByIds(vararg ids: Long): Array<HistoryModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}