package com.call.screen.themes.ui.trashfromexample

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.call.screen.themes.R
import com.call.screen.themes.databinding.ActivityPaywallBinding
import com.call.screen.themes.ui.MainActivity
import com.call.screen.themes.usecases.InterUseCase
import com.call.screen.themes.utils.Utils
import com.call.screen.themes.utils.UtilsBilling
import com.call.screen.themes.utils.prepareStatusBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class ActivityPayWall : AppCompatActivity() {

    private lateinit var binding: ActivityPaywallBinding
    private var isYearSub = true

    override fun onCreate(savedInstanceState: Bundle?) {
        prepareStatusBar(true)
        super.onCreate(savedInstanceState)
        binding = ActivityPaywallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InterUseCase.init(applicationContext)
        lifecycleScope.launch {
            UtilsBilling.startConnection(this@ActivityPayWall){

            }.onCompletion {
                update()
            }.collect{

            }
        }



        binding.ivClose.setOnClickListener {
            openNextActivity()
        }
        binding.tvLinks.movementMethod = LinkMovementMethod.getInstance()

        binding.llOfferMultiYear.setOnClickListener {
            isYearSub = true
            update()
        }
        binding.llOfferMultiMonth.setOnClickListener {
            isYearSub = false
            update()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                openNextActivity()
            }
        })
    }

    private fun update() {


        when (intent.getLongExtra(ActivityOnboard.KEY_onboardSub, 1L)) {
            1L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_1_bcg)
            //    binding.ivCard.setImageResource(R.drawable.paywall_1_card)
                binding.tvBtnContinue.setTextColor(Color.parseColor("#501455"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_1_btn)
                binding.vDot1.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                binding.vDot2.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                binding.vDot3.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                binding.vDot4.setBackgroundResource(R.drawable.onboard_dot_fill)
            }
            2L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_2_bcg)
     //           binding.ivCard.setImageResource(R.drawable.paywall_2_card)
                binding.tvBtnContinue.setTextColor(Color.parseColor("#00A2FD"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_2_btn)
                binding.vDot1.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                binding.vDot2.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                binding.vDot3.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                binding.vDot4.setBackgroundResource(R.drawable.onboard_dot_fill)
            }
            3L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_3_bcg)
        //        binding.ivCard.setImageResource(R.drawable.paywall_3_card)
                binding.tvBtnContinue.setTextColor(Color.parseColor("#FFFFFF"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_3_btn)
                binding.vDot1.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                binding.vDot2.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                binding.vDot3.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                binding.vDot4.setBackgroundResource(R.drawable.onboard_dot_fill)
            }
            4L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_4_bcg)
      //          binding.ivCard.setImageResource(R.drawable.paywall_4_card)
                binding.tvBtnContinue.setTextColor(Color.parseColor("#4E6169"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_4_btn)
                binding.vDot1.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                binding.vDot2.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                binding.vDot3.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                binding.vDot4.setBackgroundResource(R.drawable.onboard_dot_fill)
            }
            5L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_5_bcg)
   //             binding.ivCard.setImageResource(R.drawable.paywall_5_card)
                binding.tvBtnContinue.setTextColor(Color.parseColor("#FFFFFF"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_5_btn)
                binding.vDot1.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                binding.vDot2.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                binding.vDot3.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                binding.vDot4.setBackgroundResource(R.drawable.onboard_dot_fill)
            }
        }
        if (Utils.subsScreenType == 1L) {
            when (intent.getLongExtra(ActivityOnboard.KEY_onboardSub, 1L)) {
                1L -> {
                    binding.tvOfferMultiYear.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.tvOfferMultiYearDesc.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.tvOfferMultiMonth.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.tvOfferMultiMonthDesc.setTextColor(Color.parseColor("#FFFFFF"))
                    if (isYearSub) {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_1_btn_multi_selected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_1_multi_check)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_1_btn_multi_unselected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_1_multi_check_outline)
                    } else {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_1_btn_multi_unselected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_1_multi_check_outline)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_1_btn_multi_selected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_1_multi_check)
                    }
                }
                2L -> {
                    binding.tvOfferMultiYear.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.tvOfferMultiYearDesc.setTextColor(Color.parseColor("#000000"))
                    binding.tvOfferMultiMonth.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.tvOfferMultiMonthDesc.setTextColor(Color.parseColor("#000000"))
                    if (isYearSub) {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_2_btn_multi_selected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_2_multi_check)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_2_btn_multi_unselected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_2_multi_check_outline)
                    } else {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_2_btn_multi_unselected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_2_multi_check_outline)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_2_btn_multi_selected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_2_multi_check)
                    }
                }
                3L -> {
                    binding.tvOfferMultiYear.setTextColor(Color.parseColor("#1F2B41"))
                    binding.tvOfferMultiYearDesc.setTextColor(Color.parseColor("#1F2B41"))
                    binding.tvOfferMultiMonth.setTextColor(Color.parseColor("#1F2B41"))
                    binding.tvOfferMultiMonthDesc.setTextColor(Color.parseColor("#1F2B41"))
                    if (isYearSub) {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_3_btn_multi_selected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_3_multi_check)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_3_btn_multi_unselected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_3_multi_check_outline)
                    } else {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_3_btn_multi_unselected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_3_multi_check_outline)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_3_btn_multi_selected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_3_multi_check)
                    }
                }
                4L -> {
                    binding.tvOfferMultiYear.setTextColor(Color.parseColor("#4E6169"))
                    binding.tvOfferMultiYearDesc.setTextColor(Color.parseColor("#4E6169"))
                    binding.tvOfferMultiMonth.setTextColor(Color.parseColor("#4E6169"))
                    binding.tvOfferMultiMonthDesc.setTextColor(Color.parseColor("#4E6169"))
                    if (isYearSub) {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_4_btn_multi_selected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_4_multi_check)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_4_btn_multi_unselected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_4_multi_check_outline)
                    } else {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_4_btn_multi_unselected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_4_multi_check_outline)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_4_btn_multi_selected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_4_multi_check)
                    }
                }
                5L -> {
                    binding.tvOfferMultiYear.setTextColor(Color.parseColor("#0F2E53"))
                    binding.tvOfferMultiYearDesc.setTextColor(Color.parseColor("#0F2E53"))
                    binding.tvOfferMultiMonth.setTextColor(Color.parseColor("#0F2E53"))
                    binding.tvOfferMultiMonthDesc.setTextColor(Color.parseColor("#0F2E53"))
                    if (isYearSub) {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_5_btn_multi_selected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_5_multi_check)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_5_btn_multi_unselected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_5_multi_check_outline)
                    } else {
                        binding.llOfferMultiYear.setBackgroundResource(R.drawable.paywall_5_btn_multi_unselected)
                        binding.ivOfferMultiYear.setImageResource(R.drawable.paywall_5_multi_check_outline)
                        binding.llOfferMultiMonth.setBackgroundResource(R.drawable.paywall_5_btn_multi_selected)
                        binding.ivOfferMultiMonth.setImageResource(R.drawable.paywall_5_multi_check)
                    }
                }
            }
            binding.llOfferMultiYear.visibility = View.VISIBLE
            binding.llOfferMultiMonth.visibility = View.VISIBLE
            binding.llOfferSingle.visibility = View.GONE
            binding.tvOfferSingleDesc.visibility = View.GONE
            val productDetailsMonth = UtilsBilling.getProductDetailsSubsMonthLand()
            val productDetailsYear = UtilsBilling.getProductDetailsSubsYearLand()
            binding.tvOfferMultiYearDesc.text = getString(R.string.year_desc, UtilsBilling.getPriceForSubs(productDetailsYear))
            binding.tvOfferMultiMonthDesc.text = getString(R.string.month_desc, UtilsBilling.getPriceForSubs(productDetailsMonth))
            if (isYearSub) {
                binding.tvReestablish.setOnClickListener {
                    UtilsBilling.restore(this, productDetailsYear)
                }
                binding.tvBtnContinue.setOnClickListener {
                    UtilsBilling.launchBillingFlow(this, productDetailsYear) {
                        if (it) {
                            openNextActivity()
                        }
                    }
                }
            } else {
                binding.tvReestablish.setOnClickListener {
                    UtilsBilling.restore(this, productDetailsMonth)
                }
                binding.tvBtnContinue.setOnClickListener {
                    UtilsBilling.launchBillingFlow(this, productDetailsMonth) {
                        if (it) {
                            openNextActivity()
                        }
                    }
                }
            }
        } else {
            when (intent.getLongExtra(ActivityOnboard.KEY_onboardSub, 1L)) {
                1L -> {
                    binding.llOfferSingle.setBackgroundResource(R.drawable.paywall_1_btn_single)
                    binding.ivOfferSingle.setImageResource(R.drawable.paywall_1_single_check)
                    binding.tvOfferSingle.setTextColor(Color.parseColor("#140240"))
                }
                2L -> {
                    binding.llOfferSingle.setBackgroundResource(R.drawable.paywall_2_btn_single)
                    binding.ivOfferSingle.setImageResource(R.drawable.paywall_2_single_check)
                    binding.tvOfferSingle.setTextColor(Color.parseColor("#1F2B41"))
                }
                3L -> {
                    binding.llOfferSingle.setBackgroundResource(R.drawable.paywall_3_btn_single)
                    binding.ivOfferSingle.setImageResource(R.drawable.paywall_3_single_check)
                    binding.tvOfferSingle.setTextColor(Color.parseColor("#1F2B41"))
                }
                4L -> {
                    binding.llOfferSingle.setBackgroundResource(R.drawable.paywall_4_btn_single)
                    binding.ivOfferSingle.setImageResource(R.drawable.paywall_4_single_check)
                    binding.tvOfferSingle.setTextColor(Color.parseColor("#4E6169"))
                }
                5L -> {
                    binding.llOfferSingle.setBackgroundResource(R.drawable.paywall_5_btn_single)
                    binding.ivOfferSingle.setImageResource(R.drawable.paywall_5_single_check)
                    binding.tvOfferSingle.setTextColor(Color.parseColor("#0F2E53"))
                }
            }
            if (Utils.subsScreenType == 2L) {
                binding.llOfferMultiYear.visibility = View.GONE
                binding.llOfferMultiMonth.visibility = View.GONE
                binding.llOfferSingle.visibility = View.VISIBLE
                binding.tvOfferSingleDesc.visibility = View.VISIBLE
                val productDetails = UtilsBilling.getProductDetailsSubsYear()
                binding.tvOfferSingle.text = getString(R.string.year_btn_title)
                binding.tvOfferSingleDesc.text = getString(R.string.year_desc, UtilsBilling.getPriceForSubs(productDetails))
                binding.tvReestablish.setOnClickListener {
                    UtilsBilling.restore(this, productDetails)
                }
                binding.tvBtnContinue.setOnClickListener {
                    UtilsBilling.launchBillingFlow(this, productDetails) {
                        if (it) {
                            openNextActivity()
                        }
                    }
                }
            } else if (Utils.subsScreenType == 3L) {
                binding.llOfferMultiYear.visibility = View.GONE
                binding.llOfferMultiMonth.visibility = View.GONE
                binding.llOfferSingle.visibility = View.VISIBLE
                binding.tvOfferSingleDesc.visibility = View.VISIBLE
                val productDetails = UtilsBilling.getProductDetailsSubsMonth()
                binding.tvOfferSingle.text = getString(R.string.month_btn_title)
                binding.tvOfferSingleDesc.text = getString(R.string.month_desc, UtilsBilling.getPriceForSubs(productDetails))
                binding.tvReestablish.setOnClickListener {
                    UtilsBilling.restore(this, productDetails)
                }
                binding.tvBtnContinue.setOnClickListener {
                    UtilsBilling.launchBillingFlow(this, productDetails) {
                        if (it) {
                            openNextActivity()
                        }
                    }
                }
            }
        }
    }

    private fun openNextActivity() {
        lifecycleScope.launch {
            InterUseCase.show(this@ActivityPayWall)
            InterUseCase.fullScreenCallback().onCompletion {
                startActivity(Intent(this@ActivityPayWall, MainActivity::class.java))
                finish()
            }.collect()

        }

    }

}