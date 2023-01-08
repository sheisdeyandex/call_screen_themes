package com.call.screen.themes.ui.trashfromexample

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.call.screen.themes.Constants
import com.call.screen.themes.databinding.ActivitySplashBinding
import com.call.screen.themes.ui.MainActivity
import com.call.screen.themes.usecases.InterUseCase
import com.call.screen.themes.utils.Utils
import com.call.screen.themes.utils.UtilsBilling
import com.call.screen.themes.utils.prepareStatusBar
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ActivitySplash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        prepareStatusBar()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        sharedPreferences = getPreferences(MODE_PRIVATE)
        setContentView(binding.root)
        Utils.timeBeginInMillis = 0L
        Utils.isDisplayedPopupRate = false

        MobileAds.initialize(this) {}
        if(sharedPreferences.getBoolean("firstrun", true)){
            sharedPreferences.edit().putBoolean("firstrun",false).apply()
            startActivity(Intent(this@ActivitySplash, ActivityOnboard::class.java)
                .putExtra(ActivityOnboard.KEY_onboardSub, Utils.onboardSub))
            finish()
        }
        InterUseCase.init(applicationContext)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Utils.getRemoteConfig(this) {
            lifecycleScope.launch {
                UtilsBilling.startConnection(this@ActivitySplash) {

                }.collect{
                    if (it) {
                        delay(1000)
                        binding.pbLoading.visibility = View.INVISIBLE
                        startActivity(Intent(this@ActivitySplash, MainActivity::class.java)
                            .putExtra(ActivityOnboard.KEY_onboardSub, Utils.onboardSub))
                        finish()
                    } else {
                        delay(4000)
                        if((sharedPreferences.getBoolean("firstrun", true))){
                            sharedPreferences.edit().putBoolean("firstrun",false).apply()
                            binding.pbLoading.visibility = View.INVISIBLE
                            startActivity(Intent(this@ActivitySplash, ActivityOnboard::class.java)
                                .putExtra(ActivityOnboard.KEY_onboardSub, Utils.onboardSub))
                            finish()
                        }
                        else{
                            lifecycleScope.launch {
                                InterUseCase.fullScreenCallback().collect{
                                    if (it){
                                        startActivity(Intent(this@ActivitySplash, MainActivity::class.java)
                                            .putExtra(ActivityOnboard.KEY_onboardSub, Utils.onboardSub))
                                        finish()
                                    }
                                    else{
                                        startActivity(Intent(this@ActivitySplash, MainActivity::class.java)
                                            .putExtra(ActivityOnboard.KEY_onboardSub, Utils.onboardSub))
                                        finish()
                                    }
                                }
                            }
                           InterUseCase.show(this@ActivitySplash)
                        }

                    }
                }
            }

        }

        updateProgress(System.currentTimeMillis())
    }
    private fun updateProgress(startTimeInMillis: Long) {

    }
}