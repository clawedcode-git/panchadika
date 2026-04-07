package com.panchadika.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey
    val id: Long,
    val address: String,
    val contactName: String?,
    val snippet: String,
    val timestamp: Long,
    val unreadCount: Int,
    val isRead: Boolean
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id: Long,
    val threadId: Long,
    val address: String,
    val body: String,
    val timestamp: Long,
    val type: Int,
    val status: Int,
    val read: Boolean
)