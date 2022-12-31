package com.call.screen.themes.utils

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object UtilsBilling {

    private var activity: AppCompatActivity? = null
    private var bc: BillingClient? = null
    private var hmProductDetails = HashMap<String, ProductDetails>()
    private var callbackPurchase: (isSuccess: Boolean) -> Unit = {}

    fun isEnableScreenOfferPremium() = getProductDetailsSubsMonth() != null

    fun startConnection(activity: AppCompatActivity, callback: () -> Unit) {
        UtilsBilling.activity = activity
        bc = null
        hmProductDetails = HashMap()
        callbackPurchase = {}
        billingClient().startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    @OptIn(DelicateCoroutinesApi::class)
                    GlobalScope.launch {
                        var isPremiumActive = false
                        queryActivePurchases().forEach { purchase ->
                            purchase.products.forEach { sku ->
                                if (sku == Utils.monthSub || sku == Utils.yearSub || sku == Utils.monthSubLand || sku == Utils.yearSubLand) {
                                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                                        isPremiumActive = true
                                        //consumePurchasePremium(purchase)
                                    }
                                }
                            }
                        }
                        // Prefs.isPremium(activity, isPremiumActive)
                        queryProductDetailsSUBS(callback)
                    }
                } else {
                    callback()
                }
            }
            override fun onBillingServiceDisconnected() {
                callback()
            }
        })
    }

    private fun billingClient(): BillingClient {
        if (bc == null) {
            bc = BillingClient.newBuilder(activity!!)
                .setListener { billingResult, purchases ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        purchases?.forEach { purchase ->
                            if (!purchase.isAcknowledged) {
                                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken)
                                billingClient().acknowledgePurchase(acknowledgePurchaseParams.build()) { billingResult ->
                                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                        acceptPurchase(purchase)
                                    } else if (billingResult.responseCode != BillingClient.BillingResponseCode.USER_CANCELED) {
                                        callbackPurchase(false)
                                    }
                                }
                            } else {
                                acceptPurchase(purchase)
                            }
                        }
                    } else if (billingResult.responseCode != BillingClient.BillingResponseCode.USER_CANCELED) {
                        callbackPurchase(false)
                    }
                }
                .enablePendingPurchases()
                .build()
        }
        return bc!!
    }
    private fun acceptPurchase(purchase: Purchase) {
        purchase.products.forEach { sku ->
            when (sku) {
                Utils.monthSub, Utils.yearSub, Utils.monthSubLand, Utils.yearSubLand -> {
                    // Prefs.isPremium(activity!!, true)
                    callbackPurchase(true)
                }
            }
        }
    }

    private suspend fun queryActivePurchases() = billingClient().queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()).purchasesList +
            billingClient().queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()).purchasesList

    private fun queryProductDetailsSUBS(callback: () -> Unit) = billingClient()
        .queryProductDetailsAsync(
            QueryProductDetailsParams
                .newBuilder()
                .setProductList(mutableListOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Utils.monthSub)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build(),
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Utils.yearSub)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build(),
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Utils.monthSubLand)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build(),
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Utils.yearSubLand)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                ))
                .build()
        ) { _, productDetailsList ->
            productDetailsList.forEach {
                hmProductDetails[it.productId] = it
            }
            callback()
        }

    fun getProductDetailsSubsMonth() = hmProductDetails[Utils.monthSub]
    fun getProductDetailsSubsYear() = hmProductDetails[Utils.yearSub]
    fun getProductDetailsSubsMonthLand() = hmProductDetails[Utils.monthSubLand]
    fun getProductDetailsSubsYearLand() = hmProductDetails[Utils.yearSubLand]

    fun getPriceForSubs(productDetails: ProductDetails?) = if (productDetails != null && !productDetails.subscriptionOfferDetails.isNullOrEmpty()) {
        if (productDetails.subscriptionOfferDetails!![0].pricingPhases.pricingPhaseList.size > 1) {
            productDetails.subscriptionOfferDetails!![0].pricingPhases.pricingPhaseList[1].formattedPrice
        } else {
            productDetails.subscriptionOfferDetails!![0].pricingPhases.pricingPhaseList[0].formattedPrice
        }
    } else {
        ""
    }

    fun restore(activity: AppCompatActivity, productDetails: ProductDetails?) {
        productDetails ?: return
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/account/subscriptions?sku=${productDetails.productId}&package=${activity.packageName}"))
        activity.startActivity(intent)
    }

    fun launchBillingFlow(activity: AppCompatActivity, productDetails: ProductDetails?, callback: (isSuccess: Boolean) -> Unit) {
        if (productDetails == null) {
            callback(false)
            return
        }
        callbackPurchase = callback
        val flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                if (!productDetails.subscriptionOfferDetails.isNullOrEmpty()) {
                    mutableListOf(
                        BillingFlowParams.ProductDetailsParams
                            .newBuilder()
                            .setProductDetails(productDetails)
                            .setOfferToken(productDetails.subscriptionOfferDetails!![0].offerToken)
                            .build()
                    )
                } else {
                    mutableListOf(
                        BillingFlowParams.ProductDetailsParams
                            .newBuilder()
                            .setProductDetails(productDetails)
                            .build()
                    )
                }
            )
            .build()
        billingClient().launchBillingFlow(activity, flowParams)
    }
}