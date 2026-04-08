package com.panchadika.di

import com.panchadika.data.repository.CarrierRepositoryImpl
import com.panchadika.data.repository.SmsRepositoryImpl
import com.panchadika.domain.repository.CarrierRepository
import com.panchadika.domain.repository.SmsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSmsRepository(
        smsRepositoryImpl: SmsRepositoryImpl
    ): SmsRepository

    @Binds
    @Singleton
    abstract fun bindCarrierRepository(
        carrierRepositoryImpl: CarrierRepositoryImpl
    ): CarrierRepository
}