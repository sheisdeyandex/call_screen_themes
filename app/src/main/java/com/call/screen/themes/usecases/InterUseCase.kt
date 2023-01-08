package com.call.screen.themes.usecases

import android.app.Activity
import android.content.Context
import com.call.screen.themes.R
import com.call.screen.themes.singleton.CallApplication
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

object InterUseCase {
    private var mInterstitialAd: InterstitialAd? = null
    fun init(context: Context){
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context,context.getString(R.string.inter_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }
    fun fullScreenCallback()= channelFlow{
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {

            }

            override fun onAdDismissedFullScreenContent() {
                CoroutineScope(Dispatchers.IO).launch {
                    send(true)
                    close()
                }
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                CoroutineScope(Dispatchers.IO).launch {
                    send(false)
                    close()
                }
            }
            override fun onAdImpression() {
            }

            override fun onAdShowedFullScreenContent() {
                CallApplication.lastInterShowTimer = Calendar.getInstance().time
            }

        }
        if (mInterstitialAd==null){
            send(false)
            close()
        }
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun show(activity:Activity){
        mInterstitialAd?.show(activity)
        init(activity)
    }
}