package com.call.screen.themes.singleton

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.call.screen.themes.data.model.AdapterModel
import com.call.screen.themes.data.model.ContactsModel
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import kotlin.collections.ArrayList


@HiltAndroidApp
class CallApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        val config = YandexMetricaConfig.newConfigBuilder("ffdadd1e-8bf3-413a-b323-0cc7d562d3ac").build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
    companion object{
        lateinit var adapterModel:AdapterModel
        var call= MutableLiveData<Boolean>()
        var missedCall= MutableLiveData<Boolean>()
        var applyTheme= MutableLiveData<Boolean>()
        var deleteTheme= MutableLiveData<Boolean>()
        var theme=""
        var videoUri=""
        var premium= false
        var lastInterShowTimer = Calendar.getInstance().time
        var contactsScreen = ArrayList<ContactsModel>()
    }

}