package com.panchadika.data.source

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.Telephony

class SmsProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.panchadika.provider"
        
        private const val THREADS = 1
        private const val THREAD_ID = 2
        private const val MESSAGES = 3
        private const val MESSAGE_ID = 4
        private const val SMS = 5
        private const val SMS_ID = 6

        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "threads", THREADS)
            addURI(AUTHORITY, "threads/#", THREAD_ID)
            addURI(AUTHORITY, "messages", MESSAGES)
            addURI(AUTHORITY, "messages/#", MESSAGE_ID)
            addURI(AUTHORITY, "sms", SMS)
            addURI(AUTHORITY, "sms/#", SMS_ID)
        }
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val context = context ?: return null
        
        return when (uriMatcher.match(uri)) {
            THREADS -> {
                context.contentResolver.query(
                    Telephony.Sms.Conversations.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
                )
            }
            THREAD_ID -> {
                val threadId = ContentUris.parseId(uri)
                context.contentResolver.query(
                    Telephony.Sms.Conversations.CONTENT_URI,
                    projection,
                    "thread_id = ?",
                    arrayOf(threadId.toString()),
                    sortOrder
                )
            }
            SMS -> {
                context.contentResolver.query(
                    Telephony.Sms.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder ?: "${Telephony.Sms.DATE} DESC"
                )
            }
            SMS_ID -> {
                val messageId = ContentUris.parseId(uri)
                context.contentResolver.query(
                    Telephony.Sms.CONTENT_URI,
                    projection,
                    "${Telephony.Sms._ID} = ?",
                    arrayOf(messageId.toString()),
                    sortOrder
                )
            }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            THREADS -> "vnd.android.cursor.dir/multiple"
            THREAD_ID -> "vnd.android.cursor.item"
            SMS, SMS_ID -> "vnd.android.cursor.item/vnd.android-telephony.sms"
            MESSAGES, MESSAGE_ID -> "vnd.android.cursor.item/vnd.android-telephony.sms"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val context = context ?: return null
        values ?: return null

        return when (uriMatcher.match(uri)) {
            SMS -> {
                val resultUri = context.contentResolver.insert(Telephony.Sms.CONTENT_URI, values)
                resultUri
            }
            else -> null
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val context = context ?: return 0

        return when (uriMatcher.match(uri)) {
            SMS_ID -> {
                val messageId = ContentUris.parseId(uri)
                context.contentResolver.delete(
                    Telephony.Sms.CONTENT_URI,
                    "${Telephony.Sms._ID} = ?",
                    arrayOf(messageId.toString())
                )
            }
            THREAD_ID -> {
                val threadId = ContentUris.parseId(uri)
                context.contentResolver.delete(
                    Telephony.Sms.CONTENT_URI,
                    "${Telephony.Sms.THREAD_ID} = ?",
                    arrayOf(threadId.toString())
                )
            }
            else -> 0
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val context = context ?: return 0
        values ?: return 0

        return when (uriMatcher.match(uri)) {
            SMS_ID -> {
                val messageId = ContentUris.parseId(uri)
                context.contentResolver.update(
                    Telephony.Sms.CONTENT_URI,
                    values,
                    "${Telephony.Sms._ID} = ?",
                    arrayOf(messageId.toString())
                )
            }
            else -> 0
        }
    }
}