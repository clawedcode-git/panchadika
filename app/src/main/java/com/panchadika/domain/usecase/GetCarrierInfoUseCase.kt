package com.panchadika.domain.usecase

import com.panchadika.domain.model.CarrierInfo
import com.panchadika.domain.repository.CarrierRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarrierInfoUseCase @Inject constructor(
    private val carrierRepository: CarrierRepository
) {
    operator fun invoke(): Flow<CarrierInfo> {
        return carrierRepository.getCarrierInfo()
    }

    suspend fun refresh(): CarrierInfo {
        return carrierRepository.refreshCarrierInfo()
    }

    fun getSmscAddress(): String? {
        return carrierRepository.getSmscAddress()
    }
}