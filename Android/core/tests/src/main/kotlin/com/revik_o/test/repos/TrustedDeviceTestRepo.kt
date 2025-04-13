package com.revik_o.test.repos

import com.revik_o.core.data.model.TrustedDeviceModel
import com.revik_o.core.data.repository.TrustedDeviceRepositoryI

class TrustedDeviceTestRepo: TrustedDeviceRepositoryI {
    override suspend fun getById(id: Long): TrustedDeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun getByIds(vararg ids: Long): Array<TrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Array<TrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: TrustedDeviceModel): TrustedDeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(vararg entities: TrustedDeviceModel): Array<TrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(entities: List<TrustedDeviceModel>): List<TrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Long): TrustedDeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun deleteByIds(vararg ids: Long): Array<TrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}