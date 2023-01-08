package com.call.screen.themes.ui.trashfromexample

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.call.screen.themes.R
import com.call.screen.themes.databinding.ActivityOnboardBinding
import com.call.screen.themes.utils.prepareStatusBar

class ActivityOnboard : AppCompatActivity() {

    companion object {
        private const val KEY_screenNumber = "0"
        const val KEY_onboardSub = "onboardSub"
    }

    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        prepareStatusBar(true)
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivClose.setOnClickListener {
            openNextActivity()
        }
        binding.tvDescription.movementMethod = LinkMovementMethod.getInstance()
        binding.tvBtnContinue.setOnClickListener {
            if (intent.getIntExtra(KEY_screenNumber, 1) == 4) {
                openNextActivity()
            } else {
                startActivity(Intent(this, ActivityOnboard::class.java)
                    .putExtra(KEY_screenNumber, intent.getIntExtra(KEY_screenNumber, 1) + 1)
                    .putExtra(KEY_onboardSub, intent.getLongExtra(KEY_onboardSub, 1L)))
                finish()
            }
        }

        update()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                openNextActivity()
            }
        })
    }

    private fun update() {
        when (intent.getLongExtra(KEY_onboardSub, 1L)) {
            1L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_1_bcg)
                //binding.tvDescription.setLinkTextColor(Color.parseColor("#8B0B60"))
                binding.tvBtnContinue.setTextColor(Color.parseColor("#501455"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_1_btn)
                when (intent.getIntExtra(KEY_screenNumber, 1)) {
                    1 -> {
                        binding.ivClose.visibility = View.INVISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_1)
                        binding.tvTitle.text = getString(R.string.onboard_title_1)
                        //binding.tvDescription.setText(R.string.onboard_desc_1)
                        binding.tvBtnContinue.text = getString(R.string.agree)
                        binding.vDot1.visibility = View.INVISIBLE
                        binding.vDot2.visibility = View.INVISIBLE
                        binding.vDot3.visibility = View.INVISIBLE
                        binding.vDot4.visibility = View.INVISIBLE
                    }
                    2 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_2)
                        binding.tvTitle.text = getString(R.string.onboard_title_2)
                        binding.tvDescription.text = getString(R.string.onboard_desc_2)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                    }
                    3 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_3)
                        binding.tvTitle.text = getString(R.string.onboard_title_3)
                        binding.tvDescription.text = getString(R.string.onboard_desc_3)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                    }
                    4 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_4)
                        binding.tvTitle.text = getString(R.string.onboard_title_4)
                        binding.tvDescription.text = getString(R.string.onboard_desc_4)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_1_dot_empty)
                    }
                }
            }
            2L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_2_bcg)
                //binding.tvDescription.setLinkTextColor(Color.parseColor("#00D1FF"))
                binding.tvBtnContinue.setTextColor(Color.parseColor("#00A2FD"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_2_btn)
                when (intent.getIntExtra(KEY_screenNumber, 1)) {
                    1 -> {
                        binding.ivClose.visibility = View.INVISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_1)
                        binding.tvTitle.text = getString(R.string.onboard_title_1)
                        //binding.tvDescription.setText(R.string.onboard_desc_1)
                        binding.tvBtnContinue.text = getString(R.string.agree)
                        binding.vDot1.visibility = View.INVISIBLE
                        binding.vDot2.visibility = View.INVISIBLE
                        binding.vDot3.visibility = View.INVISIBLE
                        binding.vDot4.visibility = View.INVISIBLE
                    }
                    2 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_2)
                        binding.tvTitle.text = getString(R.string.onboard_title_2)
                        binding.tvDescription.text = getString(R.string.onboard_desc_2)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                    }
                    3 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_3)
                        binding.tvTitle.text = getString(R.string.onboard_title_3)
                        binding.tvDescription.text = getString(R.string.onboard_desc_3)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                    }
                    4 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_4)
                        binding.tvTitle.text = getString(R.string.onboard_title_4)
                        binding.tvDescription.text = getString(R.string.onboard_desc_4)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_2_dot_empty)
                    }
                }
            }
            3L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_3_bcg)
                //binding.tvDescription.setLinkTextColor(Color.parseColor("#4CD4FF"))
                binding.tvBtnContinue.setTextColor(Color.parseColor("#FFFFFF"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_3_btn)
                when (intent.getIntExtra(KEY_screenNumber, 1)) {
                    1 -> {
                        binding.ivClose.visibility = View.INVISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_1)
                        binding.tvTitle.text = getString(R.string.onboard_title_1)
                        //binding.tvDescription.setText(R.string.onboard_desc_1)
                        binding.tvBtnContinue.text = getString(R.string.agree)
                        binding.vDot1.visibility = View.INVISIBLE
                        binding.vDot2.visibility = View.INVISIBLE
                        binding.vDot3.visibility = View.INVISIBLE
                        binding.vDot4.visibility = View.INVISIBLE
                    }
                    2 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_2)
                        binding.tvTitle.text = getString(R.string.onboard_title_2)
                        binding.tvDescription.text = getString(R.string.onboard_desc_2)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                    }
                    3 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_3)
                        binding.tvTitle.text = getString(R.string.onboard_title_3)
                        binding.tvDescription.text = getString(R.string.onboard_desc_3)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                    }
                    4 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_4)
                        binding.tvTitle.text = getString(R.string.onboard_title_4)
                        binding.tvDescription.text = getString(R.string.onboard_desc_4)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_3_dot_empty)
                    }
                }
            }
            4L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_4_bcg)
                //binding.tvDescription.setLinkTextColor(Color.parseColor("#9CFA62"))
                binding.tvBtnContinue.setTextColor(Color.parseColor("#4E6169"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_4_btn)
                when (intent.getIntExtra(KEY_screenNumber, 1)) {
                    1 -> {
                        binding.ivClose.visibility = View.INVISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_1)
                        binding.tvTitle.text = getString(R.string.onboard_title_1)
                        //binding.tvDescription.setText(R.string.onboard_desc_1)
                        binding.tvBtnContinue.text = getString(R.string.agree)
                        binding.vDot1.visibility = View.INVISIBLE
                        binding.vDot2.visibility = View.INVISIBLE
                        binding.vDot3.visibility = View.INVISIBLE
                        binding.vDot4.visibility = View.INVISIBLE
                    }
                    2 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_2)
                        binding.tvTitle.text = getString(R.string.onboard_title_2)
                        binding.tvDescription.text = getString(R.string.onboard_desc_2)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                    }
                    3 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_3)
                        binding.tvTitle.text = getString(R.string.onboard_title_3)
                        binding.tvDescription.text = getString(R.string.onboard_desc_3)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                    }
                    4 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_4)
                        binding.tvTitle.text = getString(R.string.onboard_title_4)
                        binding.tvDescription.text = getString(R.string.onboard_desc_4)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_4_dot_empty)
                    }
                }
            }
            5L -> {
                binding.root.setBackgroundResource(R.drawable.onboard_5_bcg)
                //binding.tvDescription.setLinkTextColor(Color.parseColor("#7FAAFF"))
                binding.tvBtnContinue.setTextColor(Color.parseColor("#FFFFFF"))
                binding.tvBtnContinue.setBackgroundResource(R.drawable.onboard_5_btn)
                when (intent.getIntExtra(KEY_screenNumber, 1)) {
                    1 -> {
                        binding.ivClose.visibility = View.INVISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_1)
                        binding.tvTitle.text = getString(R.string.onboard_title_1)
                        //binding.tvDescription.setText(R.string.onboard_desc_1)
                        binding.tvBtnContinue.text = getString(R.string.agree)
                        binding.vDot1.visibility = View.INVISIBLE
                        binding.vDot2.visibility = View.INVISIBLE
                        binding.vDot3.visibility = View.INVISIBLE
                        binding.vDot4.visibility = View.INVISIBLE
                    }
                    2 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_2)
                        binding.tvTitle.text = getString(R.string.onboard_title_2)
                        binding.tvDescription.text = getString(R.string.onboard_desc_2)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                    }
                    3 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_3)
                        binding.tvTitle.text = getString(R.string.onboard_title_3)
                        binding.tvDescription.text = getString(R.string.onboard_desc_3)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                    }
                    4 -> {
                        binding.ivClose.visibility = View.VISIBLE
                        binding.ivImg.setImageResource(R.drawable.onboard_img_4)
                        binding.tvTitle.text = getString(R.string.onboard_title_4)
                        binding.tvDescription.text = getString(R.string.onboard_desc_4)
                        binding.tvBtnContinue.text = getString(R.string.continue_title)
                        binding.vDot1.visibility = View.VISIBLE
                        binding.vDot2.visibility = View.VISIBLE
                        binding.vDot3.visibility = View.VISIBLE
                        binding.vDot4.visibility = View.VISIBLE
                        binding.vDot1.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                        binding.vDot2.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                        binding.vDot3.setBackgroundResource(R.drawable.onboard_dot_fill)
                        binding.vDot4.setBackgroundResource(R.drawable.onboard_5_dot_empty)
                    }
                }
            }
        }
    }

    private fun openNextActivity() {
        startActivity(Intent(this, ActivityPayWall::class.java)
            .putExtra(KEY_onboardSub, intent.getLongExtra(KEY_onboardSub, 1L)))
        finish()
    }

}