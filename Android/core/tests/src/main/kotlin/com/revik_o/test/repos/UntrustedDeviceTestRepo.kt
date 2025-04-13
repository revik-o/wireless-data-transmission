package com.revik_o.test.repos

import com.revik_o.core.data.model.UntrustedDeviceModel
import com.revik_o.core.data.repository.UntrustedDeviceRepositoryI

class UntrustedDeviceTestRepo: UntrustedDeviceRepositoryI {
    override suspend fun getById(id: Long): UntrustedDeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun getByIds(vararg ids: Long): Array<UntrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Array<UntrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: UntrustedDeviceModel): UntrustedDeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(vararg entities: UntrustedDeviceModel): Array<UntrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(entities: List<UntrustedDeviceModel>): List<UntrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Long): UntrustedDeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun deleteByIds(vararg ids: Long): Array<UntrustedDeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}