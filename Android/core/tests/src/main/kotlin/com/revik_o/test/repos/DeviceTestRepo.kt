package com.revik_o.test.repos

import com.revik_o.core.data.model.DeviceModel
import com.revik_o.core.data.repository.DeviceModelRepositoryI

class DeviceTestRepo: DeviceModelRepositoryI {

    override suspend fun getById(id: Long): DeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun getByIds(vararg ids: Long): Array<DeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Array<DeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: DeviceModel): DeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(vararg entities: DeviceModel): Array<DeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(entities: List<DeviceModel>): List<DeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Long): DeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun deleteByIds(vararg ids: Long): Array<DeviceModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}