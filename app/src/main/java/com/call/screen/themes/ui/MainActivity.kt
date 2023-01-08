package com.call.screen.themes.ui

import android.Manifest.permission.*
import android.app.Activity
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.telecom.TelecomManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.utils.Utils
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var rateApp = MutableLiveData<Boolean>()
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    POST_NOTIFICATIONS
                ),10
            )
        }
        sharedPreferences = getPreferences(MODE_PRIVATE)
        if (sharedPreferences.getBoolean(Constants.sharedPreferencesShowRateApp,true)){
            if(sharedPreferences.getBoolean("firstrun", true)){
                runTimer( Utils.rateFirstDelaySec)
            }
            else{
                runTimer(Utils.rateSecondDelaySec)
            }
        }


    }
    fun runTimer(delay:Long){
        object :CountDownTimer(delay*1000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                rateApp.postValue(true)
            }
        }.start()
    }

    override fun onResume() {
        super.onResume()
        if (sharedPreferences.getBoolean("firstrun", true)) {
            sharedPreferences.edit().putBoolean("firstrun", false).apply()
        }
    }
}