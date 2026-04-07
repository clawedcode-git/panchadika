package com.panchadika.data.repository

import com.panchadika.data.source.CarrierDataSource
import com.panchadika.domain.model.CarrierInfo
import com.panchadika.domain.repository.CarrierRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarrierRepositoryImpl @Inject constructor(
    private val carrierDataSource: CarrierDataSource
) : CarrierRepository {

    private val _carrierInfo = MutableStateFlow(CarrierInfo.empty())

    override fun getCarrierInfo(): Flow<CarrierInfo> = _carrierInfo.asStateFlow()

    override suspend fun refreshCarrierInfo(): CarrierInfo {
        val info = carrierDataSource.getCarrierInfo()
        _carrierInfo.value = info
        return info
    }

    override fun getSmscAddress(): String? {
        return carrierDataSource.getSmscAddress()
    }
}