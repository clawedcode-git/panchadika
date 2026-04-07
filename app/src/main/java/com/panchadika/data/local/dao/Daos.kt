package com.panchadika.data.local.dao

import androidx.room.*
import com.panchadika.data.local.entity.ConversationEntity
import com.panchadika.data.local.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Query("SELECT * FROM conversations ORDER BY timestamp DESC")
    fun getAllConversations(): Flow<List<ConversationEntity>>

    @Query("SELECT * FROM conversations WHERE id = :threadId")
    suspend fun getConversation(threadId: Long): ConversationEntity?

    @Query("SELECT * FROM conversations WHERE address = :address")
    suspend fun getConversationByAddress(address: String): ConversationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: ConversationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversations(conversations: List<ConversationEntity>)

    @Delete
    suspend fun deleteConversation(conversation: ConversationEntity)

    @Query("DELETE FROM conversations WHERE id = :threadId")
    suspend fun deleteConversationById(threadId: Long)

    @Query("UPDATE conversations SET unreadCount = 0, isRead = 1 WHERE id = :threadId")
    suspend fun markAsRead(threadId: Long)
}

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE threadId = :threadId ORDER BY timestamp ASC")
    fun getMessagesByThread(threadId: Long): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE address = :address ORDER BY timestamp ASC")
    fun getMessagesByAddress(address: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE id = :messageId")
    suspend fun getMessage(messageId: Long): MessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Query("DELETE FROM messages WHERE id = :messageId")
    suspend fun deleteMessageById(messageId: Long)

    @Query("DELETE FROM messages WHERE threadId = :threadId")
    suspend fun deleteMessagesByThread(threadId: Long)

    @Query("UPDATE messages SET read = 1 WHERE threadId = :threadId")
    suspend fun markMessagesAsRead(threadId: Long)

    @Query("UPDATE messages SET status = :status WHERE id = :messageId")
    suspend fun updateMessageStatus(messageId: Long, status: Int)
}