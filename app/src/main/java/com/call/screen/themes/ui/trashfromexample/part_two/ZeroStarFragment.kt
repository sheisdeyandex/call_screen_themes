package com.call.screen.themes.ui.trashfromexample.part_two
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.databinding.BottomSheet2ZeroStarBinding
import com.call.screen.themes.extension.BaseFragment
import com.call.screen.themes.extension.gone
import com.call.screen.themes.extension.hideKeyboard
import com.call.screen.themes.extension.visible
import com.call.screen.themes.ui.MainActivity
import com.call.screen.themes.usecases.SentEmail

class ZeroStarFragment : BaseFragment<BottomSheet2ZeroStarBinding>(
    BottomSheet2ZeroStarBinding::inflate
) {
    var starsToRate = 0

    private fun makeStars(stars: Int){
        if (stars==1){
            binding.oneStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.twoStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.threeStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.fourStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.fiveStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.imageView3.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_angry))
            binding.rateButton.text = getString(R.string.rate)
            binding.rateButton.setTextColor(Color.parseColor("#0277BD"))
        }
        if (stars==2){
            binding.oneStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.twoStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.threeStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.fourStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.fiveStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.imageView3.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_upset))
            binding.rateButton.text = getString(R.string.rate)
            binding.rateButton.setTextColor(Color.parseColor("#0277BD"))
        }
        if (stars==3){
            binding.oneStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.twoStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.threeStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.fourStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.fiveStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.imageView3.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.three_stars_emoji_part_two))
            binding.rateButton.text = getString(R.string.rate)
            binding.rateButton.setTextColor(Color.parseColor("#0277BD"))
        }
        if (stars==4){
            binding.oneStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.twoStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.threeStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.fourStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.fiveStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_star_2))
            binding.imageView3.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.four_stars_emoji_part_two))
            binding.rateButton.text = getString(R.string.rate)
            binding.rateButton.setTextColor(Color.parseColor("#0277BD"))
        }
        if (stars==5){
            binding.oneStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.twoStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.threeStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.fourStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.fiveStarIv.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.orange_star))
            binding.imageView3.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.five_stars_emoji_part_two))
            binding.rateButton.text = getString(R.string.rate_on_google_play)
            binding.rateButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
        }
        starsToRate = stars
    }
    override fun setupClickListener() = with(binding) {
        binding.oneStarIv.setOnClickListener {
            makeStars(1)
        }

        binding.twoStarIv.setOnClickListener {
            makeStars(2)
        }

        binding.threeStarIv.setOnClickListener {
            makeStars(3)
        }

        binding.fourStarIv.setOnClickListener {
            makeStars(4)
        }

        binding.fiveStarIv.setOnClickListener {
            makeStars(5)
        }

        binding.rateButton.setOnClickListener {
            if (starsToRate==5){
                (requireActivity() as MainActivity).sharedPreferences.edit().putBoolean(Constants.sharedPreferencesShowRateApp,false).apply()
                rateAppOnGooglePlay()
            }
            else{
                (requireActivity() as MainActivity).sharedPreferences.edit().putBoolean(Constants.sharedPreferencesShowRateApp,false).apply()
                binding.clRateApp.isVisible=false
                binding.clFeedback.isVisible=true
            }
        }
        binding.noButton.setOnClickListener {
            (requireActivity() as MainActivity).sharedPreferences.edit().putBoolean(Constants.sharedPreferencesShowRateApp,false).apply()
            binding.clDoYouLike.isVisible=false
            binding.clFeedback.isVisible= true
        }
        binding.yesButton.setOnClickListener {
            binding.clDoYouLike.isVisible=false
            binding.clRateApp.isVisible= true
        }
        binding.submitButton.setOnClickListener {
            val message = binding.feedBackEditText.text.toString()
            // Firebase(requireContext()).sendFeedback(message)
            SentEmail.sendEmail(message, activity)
            findNavController().popBackStack()

        }
        binding.feedBackEditText.setOnClickListener {
            binding.submitButton.gone()
            binding.fabSend.visible()
        }

        binding.feedBackEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.submitButton.gone()
                binding.fabSend.visible()
            } else {
                hideKeyboard()
                binding.fabSend.gone()
                binding.submitButton.visible()
            }
        }
        binding.fabSend.setOnClickListener {
            val message = binding.feedBackEditText.text.toString()
            //      Firebase(requireContext()).sendFeedback(message)
            hideKeyboard()
            binding.fabSend.gone()
            binding.submitButton.visible()
        }
        binding.closeButton.setOnClickListener {
            (requireActivity() as MainActivity).sharedPreferences.edit().putBoolean(Constants.sharedPreferencesShowRateApp,false).apply()
            findNavController().popBackStack()
        }
    }

    private fun rateAppOnGooglePlay() {
        (requireActivity() as MainActivity).sharedPreferences.edit().putBoolean(Constants.sharedPreferencesShowRateApp,false).apply()
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data =
            Uri.parse("https://play.google.com/store/apps/details?id="+requireContext().packageName)
        context?.startActivity(openURL)
    }
}