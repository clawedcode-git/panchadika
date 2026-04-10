package com.panchadika.data.source

import android.app.PendingIntent
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsManager
import com.panchadika.domain.model.Conversation
import com.panchadika.domain.model.Message
import com.panchadika.domain.model.MessageStatus
import com.panchadika.domain.model.MessageType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val contentResolver: ContentResolver = context.contentResolver
    private val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        context.getSystemService(SmsManager::class.java)
    } else {
        SmsManager.getDefault()
    }

    fun getConversations(): List<Conversation> {
        val conversations = mutableListOf<Conversation>()
        
        val uri = Telephony.Sms.Conversations.CONTENT_URI
        val projection = arrayOf(
            "thread_id",
            "snippet"
        )

        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val threadIdIndex = cursor.getColumnIndex("thread_id")
            val snippetIndex = cursor.getColumnIndex("snippet")
            
            while (cursor.moveToNext()) {
                val threadId = cursor.getLong(threadIdIndex)
                val snippet = cursor.getString(snippetIndex) ?: ""
                
                // Get address and date from the first message in this thread
                val (address, timestamp) = getAddressAndDateForThread(threadId)
                
                conversations.add(Conversation(
                    id = threadId,
                    address = address,
                    contactName = address,
                    snippet = snippet,
                    timestamp = timestamp,
                    unreadCount = 0,
                    isRead = true
                ))
            }
        }
        return conversations
    }

    private fun getAddressAndDateForThread(threadId: Long): Pair<String, Long> {
        val uri = Telephony.Sms.CONTENT_URI
        val selection = "${Telephony.Sms.THREAD_ID} = ?"
        val selectionArgs = arrayOf(threadId.toString())
        val projection = arrayOf(Telephony.Sms.ADDRESS, Telephony.Sms.DATE)
        
        contentResolver.query(uri, projection, selection, selectionArgs, "date DESC LIMIT 1")?.use { cursor ->
            if (cursor.moveToFirst()) {
                val address = cursor.getString(0) ?: ""
                val date = cursor.getLong(1)
                return address to date
            }
        }
        return "" to System.currentTimeMillis()
    }

    fun getMessagesForThread(threadId: Long): List<Message> {
        val messages = mutableListOf<Message>()
        
        // Use inbox URI which has fewer restrictions
        val uri = Telephony.Sms.Inbox.CONTENT_URI
        val selection = "${Telephony.Sms.THREAD_ID} = ?"
        val selectionArgs = arrayOf(threadId.toString())
        val projection = arrayOf(
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE,
            Telephony.Sms.TYPE,
            Telephony.Sms.STATUS,
            Telephony.Sms.READ
        )

        contentResolver.query(uri, projection, selection, selectionArgs, "${Telephony.Sms.DATE} ASC")?.use { cursor ->
            while (cursor.moveToNext()) {
                val message = cursorToMessage(cursor, threadId)
                messages.add(message)
            }
        }
        return messages
    }

    fun getMessagesByAddress(address: String): List<Message> {
        val messages = mutableListOf<Message>()
        
        // Use inbox URI which has fewer restrictions
        val uri = Telephony.Sms.Inbox.CONTENT_URI
        val selection = "${Telephony.Sms.ADDRESS} = ?"
        val selectionArgs = arrayOf(address)
        val projection = arrayOf(
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE,
            Telephony.Sms.TYPE,
            Telephony.Sms.STATUS,
            Telephony.Sms.READ,
            Telephony.Sms.THREAD_ID
        )

        contentResolver.query(uri, projection, selection, selectionArgs, "${Telephony.Sms.DATE} ASC")?.use { cursor ->
            while (cursor.moveToNext()) {
                val threadId = cursor.getLong(7)
                val message = cursorToMessage(cursor, threadId)
                messages.add(message)
            }
        }
        return messages
    }

    private fun cursorToMessage(cursor: Cursor, threadId: Long): Message {
        val id = cursor.getLong(0)
        val address = cursor.getString(1) ?: ""
        val body = cursor.getString(2) ?: ""
        val timestamp = cursor.getLong(3)
        val type = cursor.getInt(4)
        val status = cursor.getInt(5)
        val read = cursor.getInt(6) == 1

        val messageType = when (type) {
            Telephony.Sms.MESSAGE_TYPE_INBOX -> MessageType.RECEIVED
            Telephony.Sms.MESSAGE_TYPE_SENT -> MessageType.SENT
            else -> MessageType.RECEIVED
        }

        val messageStatus = when {
            status == Telephony.Sms.STATUS_COMPLETE -> MessageStatus.SUCCESS
            status == Telephony.Sms.STATUS_PENDING -> MessageStatus.PENDING
            status == Telephony.Sms.STATUS_FAILED -> MessageStatus.FAILED
            else -> MessageStatus.NONE
        }

        return Message(
            id = id,
            threadId = threadId,
            address = address,
            body = body,
            timestamp = timestamp,
            type = messageType,
            status = messageStatus,
            read = read
        )
    }

    suspend fun sendSms(address: String, body: String): Long = withContext(Dispatchers.IO) {
        try {
            val threadId = getOrCreateThreadId(address)
            val values = ContentValues().apply {
                put(Telephony.Sms.ADDRESS, address)
                put(Telephony.Sms.BODY, body)
                put(Telephony.Sms.DATE, System.currentTimeMillis())
                put(Telephony.Sms.TYPE, Telephony.Sms.MESSAGE_TYPE_SENT)
                put(Telephony.Sms.STATUS, Telephony.Sms.STATUS_PENDING)
                put(Telephony.Sms.THREAD_ID, threadId)
            }

            val uri = contentResolver.insert(Telephony.Sms.CONTENT_URI, values)

            try {
                val smsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    context.getSystemService(SmsManager::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    SmsManager.getDefault()
                }
                val parts = smsManager.divideMessage(body)
                val sentIntents = ArrayList<PendingIntent>(parts.size)
                val deliveredIntents = ArrayList<PendingIntent>(parts.size)
                for (i in parts.indices) {
                    sentIntents.add(
                        PendingIntent.getBroadcast(
                            context,
                            i,
                            Intent("com.panchadika.SMS_SENT"),
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    )
                    deliveredIntents.add(
                        PendingIntent.getBroadcast(
                            context,
                            i,
                            Intent("com.panchadika.SMS_DELIVERED"),
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    )
                }
                smsManager.sendMultipartTextMessage(address, null, parts, sentIntents, deliveredIntents)
            } catch (e: Exception) {
                // SMS sending failed - just continue
            }

            uri?.lastPathSegment?.toLongOrNull() ?: -1L
        } catch (e: Exception) {
            e.printStackTrace()
            -1L
        }
    }

    fun markThreadAsRead(threadId: Long) {
        val uri = ContentUris.withAppendedId(Telephony.Sms.Conversations.CONTENT_URI, threadId)
        val values = ContentValues().apply {
            put("read", 1)
        }
        contentResolver.update(uri, values, null, null)
    }

    fun deleteThread(threadId: Long): Boolean {
        val uri = ContentUris.withAppendedId(Telephony.Sms.CONTENT_URI, threadId)
        return contentResolver.delete(uri, null, null) > 0
    }

    fun deleteMessage(messageId: Long): Boolean {
        val uri = ContentUris.withAppendedId(Telephony.Sms.CONTENT_URI, messageId)
        return contentResolver.delete(uri, null, null) > 0
    }

    fun getOrCreateThreadId(address: String): Long {
        return Telephony.Threads.getOrCreateThreadId(context, address)
    }
}