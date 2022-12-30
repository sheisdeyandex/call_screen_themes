package com.call.screen.themes.services.broadcasts

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.call.screen.themes.Constants
import com.call.screen.themes.services.CallService
import com.call.screen.themes.services.CallUiService
import com.call.screen.themes.singleton.CallApplication
import com.call.screen.themes.usecases.PhonecallReceiverUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class PhoneCallReceiver:PhonecallReceiverUseCase() {
    var service = CallService::class.java
    override fun onIncomingCallReceived(ctx: Context?, number: String?, start: Date?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

        }
        else{
            val intent = Intent(ctx, service)
            intent.putExtra(Constants.sharedPrefsContactsName, number)
            ctx?.startForegroundService(intent)
        }

    }

    override fun onIncomingCallAnswered(ctx: Context?, number: String?, start: Date?) {

    }

    override fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
   //     ctx?.stopService(Intent(ctx,service))
    }

    override fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {

    }

    override fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {

    }

    override fun onMissedCall(ctx: Context?, number: String?, start: Date?) {
        CoroutineScope(Dispatchers.IO).launch {
            CallApplication.missedCall.postValue(true)
            delay(1000)
            CallApplication.missedCall.postValue(false)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

        }
        else{
          //  ctx?.stopService(Intent(ctx,service))
        }

    }
}