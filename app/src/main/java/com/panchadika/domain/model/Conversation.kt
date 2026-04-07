package com.panchadika.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Conversation(
    val id: Long,
    val address: String,
    val contactName: String?,
    val snippet: String,
    val timestamp: Long,
    val unreadCount: Int,
    val isRead: Boolean
) : Parcelable

@Parcelize
data class Message(
    val id: Long,
    val threadId: Long,
    val address: String,
    val body: String,
    val timestamp: Long,
    val type: MessageType,
    val status: MessageStatus,
    val read: Boolean
) : Parcelable

enum class MessageType {
    RECEIVED,
    SENT
}

enum class MessageStatus {
    NONE,
    PENDING,
    SUCCESS,
    FAILED
}