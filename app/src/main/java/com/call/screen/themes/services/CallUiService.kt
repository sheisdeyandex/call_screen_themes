package com.call.screen.themes.services

import android.Manifest
import android.Manifest.permission.READ_CONTACTS
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.telecom.Call
import android.telecom.CallScreeningService
import android.telecom.TelecomManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.data.model.MainModel
import com.call.screen.themes.singleton.CallApplication
import com.call.screen.themes.ui.CallScreenActivity
import com.call.screen.themes.usecases.ContactsUseCase
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CallUiService:CallScreeningService() {
    var numberText = ""
    var name = ""
    var photo:String=""

    override fun onScreenCall(callDetails: Call.Details){
        if (callDetails.callDirection==0){

            numberText = callDetails.handle.schemeSpecificPart
            val intent = Intent(this, CallService::class.java)
            intent.putExtra(Constants.number, numberText)
            CoroutineScope(Dispatchers.IO).launch {
                if (ContextCompat.checkSelfPermission(applicationContext,READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
                    ContactsUseCase.getContactList(applicationContext).forEach {
                        if (it.number.replace(" ","").replace("-","")==numberText){
                            name = it.name
                            it.photo?.let {
                                photo = it
                            }
                        }

                    }.also {
                        intent.putExtra(Constants.name, name)
                        intent.putExtra(Constants.photo, photo)
                        respondToCall(callDetails, CallResponse.Builder().build())
                        startForegroundService(intent)
                    }
                }
                else{
                    intent.putExtra(Constants.name, name)
                    intent.putExtra(Constants.photo, photo)
                    respondToCall(callDetails, CallResponse.Builder().build())
                    startForegroundService(intent)
                }

            }
        }}

}