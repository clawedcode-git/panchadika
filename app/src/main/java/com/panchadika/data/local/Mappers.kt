package com.panchadika.data.local

import com.panchadika.data.local.entity.ConversationEntity
import com.panchadika.data.local.entity.MessageEntity
import com.panchadika.domain.model.Conversation
import com.panchadika.domain.model.Message
import com.panchadika.domain.model.MessageStatus
import com.panchadika.domain.model.MessageType

object DataMappers {

    fun ConversationEntity.toDomain(): Conversation {
        return Conversation(
            id = id,
            address = address,
            contactName = contactName,
            snippet = snippet,
            timestamp = timestamp,
            unreadCount = unreadCount,
            isRead = isRead
        )
    }

    fun Conversation.toEntity(): ConversationEntity {
        return ConversationEntity(
            id = id,
            address = address,
            contactName = contactName,
            snippet = snippet,
            timestamp = timestamp,
            unreadCount = unreadCount,
            isRead = isRead
        )
    }

    fun MessageEntity.toDomain(): Message {
        return Message(
            id = id,
            threadId = threadId,
            address = address,
            body = body,
            timestamp = timestamp,
            type = MessageType.entries[type],
            status = MessageStatus.entries[status],
            read = read
        )
    }

    fun Message.toEntity(): MessageEntity {
        return MessageEntity(
            id = id,
            threadId = threadId,
            address = address,
            body = body,
            timestamp = timestamp,
            type = type.ordinal,
            status = status.ordinal,
            read = read
        )
    }
}