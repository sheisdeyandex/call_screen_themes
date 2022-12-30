package com.call.screen.themes.services

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telecom.Call
import android.telecom.InCallService
import android.telephony.TelephonyManager
import android.util.Log
import com.call.screen.themes.Constants
import com.call.screen.themes.ui.CallScreenActivity


class IncomingCallService:InCallService() {
    override fun onConnectionEvent(call: Call?, event: String?, extras: Bundle?) {
        super.onConnectionEvent(call, event, extras)
        if (event != null) {
            Log.d("suka",event)
        }
    }

    override fun onCallAdded(call: Call?) {
        super.onCallAdded(call)
         (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (call?.details?.state == Call.STATE_RINGING){
                    val intent = Intent(this, CallScreenActivity::class.java)
                    intent.putExtra(Constants.sharedPrefsContactsName,true)
                    intent.putExtra(Constants.number,call?.details?.handle?.schemeSpecificPart)
                    intent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }

            } else {
                TODO("VERSION.SDK_INT < S")
            }
        )

    }
}