package com.panchadika.data.source

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Telephony
import android.util.Log

class MmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.WAP_PUSH_RECEIVED_ACTION) {
            return
        }

        val mimeType = intent.getStringExtra("mime_type")
        if (mimeType != "application/vnd.wap.mms-message") {
            return
        }

        try {
            val data = intent.getByteArrayExtra("data")
            if (data != null) {
                val values = ContentValues().apply {
                    put("address", "")
                    put("body", "")
                    put("date", System.currentTimeMillis())
                    put("read", 0)
                }
                context.contentResolver.insert(Uri.parse("content://mms/inbox"), values)
            }
        } catch (e: Exception) {
            Log.e("MmsReceiver", "Error processing MMS", e)
        }
    }
}