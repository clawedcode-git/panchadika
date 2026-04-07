package com.panchadika.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.panchadika.data.local.dao.ConversationDao
import com.panchadika.data.local.dao.MessageDao
import com.panchadika.data.local.entity.ConversationEntity
import com.panchadika.data.local.entity.MessageEntity

@Database(
    entities = [ConversationEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PanchadikaDatabase : RoomDatabase() {
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao

    companion object {
        const val DATABASE_NAME = "panchadika_db"
    }
}