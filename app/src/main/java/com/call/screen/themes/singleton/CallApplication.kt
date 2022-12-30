package com.call.screen.themes.singleton

import android.app.Application
import android.telecom.Call
import android.telecom.CallScreeningService.CallResponse
import androidx.lifecycle.MutableLiveData
import com.call.screen.themes.data.model.AdapterModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CallApplication:Application() {
    override fun onCreate() {
        super.onCreate()
    }
    companion object{
        lateinit var adapterModel:AdapterModel
        var call= MutableLiveData<Boolean>()
        var missedCall= MutableLiveData<Boolean>()
        var applyTheme= MutableLiveData<Boolean>()
    }

}