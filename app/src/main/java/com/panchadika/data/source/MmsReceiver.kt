package com.panchadika.data.source

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            return
        }

        // MMS is handled by the system and delivered to the default SMS app
        // This receiver is registered for potential future MMS handling
    }
}