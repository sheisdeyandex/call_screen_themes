package com.call.screen.themes.utils

import android.content.Context
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

fun AppCompatActivity.prepareStatusBar(isFullscreen: Boolean = false) {
    if (isFullscreen) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

object Utils {

    /*
    Сейчас: withOnboardsAndPaywalls == false
    1. Сплеш, на котором появляется политика конфиденциальности
    2. Экран настроек
    3. Пейвол
    4. Реклама

    Для наших онбордингов надо будет: withOnboardsAndPaywalls == true
    1. Обычный сплеш, со спиннером без кнопки
    2. Онбординги наши (1й экран с политикой конфиденциальности)
    3. Пейволы наши
    4. Настройки
    5. Реклама
    */

    var timeLastImpressionAdsInMillis = 0L
    var timeBeginInMillis = 0L
    var isDisplayedPopupRate = false

    var showSubScreen = 1L
    var onboardSub = 1L
    var adsDelaySec = 10L
    var numberAdsImpressions = 15L
    var withOnboardsAndPaywalls = true
    var subsScreenType = 1L
    var monthSub = "month349"
    var yearSub = "year2390"
    var monthSubLand = "3days.month349"
    var yearSubLand = "7days.year2790"
    private var rateFirstDelaySec = 90L
    private var rateSecondDelaySec = 45L
    private const val show_sub_screen = "show_sub_screen"
    private const val onboard_sub = "onboard_sub"
    private const val ads_delay_sec = "ads_delay_sec"
    private const val number_ads_impressions = "number_ads_impressions"
    private const val with_onboards_and_paywalls = "with_onboards_and_paywalls"
    private const val subs_screen_type = "subs_screen_type"
    private const val month_sub = "month_sub"
    private const val year_sub = "year_sub"
    private const val month_sub_land = "month_sub_land"
    private const val year_sub_land = "year_sub_land"
    private const val rate_first_delay_sec = "rate_first_delay_sec"
    private const val rate_second_delay_sec = "rate_second_delay_sec"
    fun getRemoteConfig(context: Context, callback: (String) -> Unit) {
        val firebaseApp = FirebaseApp.initializeApp(context) ?: run {
            callback("")
            return
        }
        val remoteConfig = Firebase.remoteConfig(firebaseApp)
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        })
        remoteConfig.setDefaultsAsync(hashMapOf<String, Any>(
            show_sub_screen to showSubScreen,
            onboard_sub to onboardSub,
            ads_delay_sec to adsDelaySec,
            number_ads_impressions to numberAdsImpressions,
            with_onboards_and_paywalls to withOnboardsAndPaywalls,
            subs_screen_type to subsScreenType,
            month_sub to monthSub,
            year_sub to yearSub,
            month_sub_land to monthSubLand,
            year_sub_land to yearSubLand,
            rate_first_delay_sec to rateFirstDelaySec,
            rate_second_delay_sec to rateSecondDelaySec
        ))
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSubScreen = remoteConfig.getLong(show_sub_screen)
                    onboardSub = remoteConfig.getLong(onboard_sub)
                    adsDelaySec = remoteConfig.getLong(ads_delay_sec)
                    numberAdsImpressions = remoteConfig.getLong(number_ads_impressions)
                    withOnboardsAndPaywalls = remoteConfig.getBoolean(with_onboards_and_paywalls)
                    subsScreenType = remoteConfig.getLong(subs_screen_type)
                    monthSub = remoteConfig.getString(month_sub)
                    yearSub = remoteConfig.getString(year_sub)
                    monthSubLand = remoteConfig.getString(month_sub_land)
                    yearSubLand = remoteConfig.getString(year_sub_land)
                    rateFirstDelaySec = remoteConfig.getLong(rate_first_delay_sec)
                    rateSecondDelaySec = remoteConfig.getLong(rate_second_delay_sec)
                    val sb = StringBuilder()
                    remoteConfig.all.forEach {
                        sb.append(it.key).append(" = ").append(it.value.asString()).append("\n")
                    }
                    callback(sb.toString())
                } else {
                    callback(task.exception?.message ?: "RC result is false")
                }
            }
    }

}