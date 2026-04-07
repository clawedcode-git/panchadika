package com.panchadika.di

import android.content.Context
import androidx.room.Room
import com.panchadika.data.local.PanchadikaDatabase
import com.panchadika.data.local.dao.ConversationDao
import com.panchadika.data.local.dao.MessageDao
import com.panchadika.data.repository.CarrierRepositoryImpl
import com.panchadika.data.repository.SmsRepositoryImpl
import com.panchadika.domain.repository.CarrierRepository
import com.panchadika.domain.repository.SmsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PanchadikaDatabase {
        return Room.databaseBuilder(
            context,
            PanchadikaDatabase::class.java,
            PanchadikaDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideConversationDao(database: PanchadikaDatabase): ConversationDao {
        return database.conversationDao()
    }

    @Provides
    @Singleton
    fun provideMessageDao(database: PanchadikaDatabase): MessageDao {
        return database.messageDao()
    }
}

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