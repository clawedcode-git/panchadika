package com.panchadika.domain.repository

import com.panchadika.domain.model.CarrierInfo
import com.panchadika.domain.model.Conversation
import com.panchadika.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface SmsRepository {
    fun getConversations(): Flow<List<Conversation>>
    fun getMessages(threadId: Long): Flow<List<Message>>
    fun getMessagesByAddress(address: String): Flow<List<Message>>
    suspend fun sendSms(address: String, body: String): Result<Message>
    suspend fun markAsRead(threadId: Long)
    suspend fun deleteConversation(threadId: Long)
    suspend fun deleteMessage(messageId: Long)
}

interface CarrierRepository {
    fun getCarrierInfo(): Flow<CarrierInfo>
    suspend fun refreshCarrierInfo(): CarrierInfo
    fun getSmscAddress(): String?
}