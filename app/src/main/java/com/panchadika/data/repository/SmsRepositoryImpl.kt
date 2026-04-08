package com.panchadika.data.repository

import com.panchadika.data.source.ContactDataSource
import com.panchadika.data.source.SmsDataSource
import com.panchadika.domain.model.Conversation
import com.panchadika.domain.model.Message
import com.panchadika.domain.model.MessageStatus
import com.panchadika.domain.model.MessageType
import com.panchadika.domain.repository.SmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsRepositoryImpl @Inject constructor(
    private val smsDataSource: SmsDataSource,
    private val contactDataSource: ContactDataSource
) : SmsRepository {

    override fun getConversations(): Flow<List<Conversation>> = flow {
        val conversations = smsDataSource.getConversations()
        val result = conversations.map { (threadId, address) ->
            val contactName = contactDataSource.getContactByAddress(address)
            
            val messages = smsDataSource.getMessagesByAddress(address)
            val lastMessage = messages.lastOrNull()
            val unreadCount = messages.count { !it.read && it.type == MessageType.RECEIVED }
            
            Conversation(
                id = threadId,
                address = address,
                contactName = contactName ?: address,
                snippet = lastMessage?.body ?: "",
                timestamp = lastMessage?.timestamp ?: System.currentTimeMillis(),
                unreadCount = unreadCount,
                isRead = unreadCount == 0
            )
        }.sortedByDescending { it.timestamp }

        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getMessages(threadId: Long): Flow<List<Message>> {
        return flow {
            val messages = smsDataSource.getMessagesForThread(threadId)
            emit(messages)
        }.flowOn(Dispatchers.IO)
    }

    override fun getMessagesByAddress(address: String): Flow<List<Message>> {
        return flow {
            val messages = smsDataSource.getMessagesByAddress(address)
            emit(messages)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun sendSms(address: String, body: String): Result<Message> {
        return try {
            val messageId = smsDataSource.sendSms(address, body)
            if (messageId > 0) {
                val threadId = smsDataSource.getOrCreateThreadId(address)
                val message = Message(
                    id = messageId,
                    threadId = threadId,
                    address = address,
                    body = body,
                    timestamp = System.currentTimeMillis(),
                    type = MessageType.SENT,
                    status = MessageStatus.SUCCESS,
                    read = true
                )
                Result.success(message)
            } else {
                Result.failure(Exception("Failed to send message"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsRead(threadId: Long) {
        try {
            smsDataSource.markThreadAsRead(threadId)
        } catch (e: Exception) {
            // Handle silently
        }
    }

    override suspend fun deleteConversation(threadId: Long) {
        try {
            smsDataSource.deleteThread(threadId)
        } catch (e: Exception) {
            // Handle silently
        }
    }

    override suspend fun deleteMessage(messageId: Long) {
        try {
            smsDataSource.deleteMessage(messageId)
        } catch (e: Exception) {
            // Handle silently
        }
    }
}