package com.call.screen.themes.ui.dialogs

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.call.screen.themes.databinding.DialogApplyThemeBinding
import com.call.screen.themes.singleton.CallApplication


class DialogApplyTheme:DialogFragment() {
    var binding: DialogApplyThemeBinding? = null
    private fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val displayMetrics = Resources.getSystem().displayMetrics
        val rect = displayMetrics.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogApplyThemeBinding.inflate(inflater, container,false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    //    dialog?.window?.setGravity(Gravity.BOTTOM)
        val wmlp = dialog?.window?.attributes
        wmlp?.gravity = Gravity.BOTTOM
        wmlp?.y = 100
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(100)
        initClicks()


    }

    private fun initClicks(){
        binding?.tvCancel?.setOnClickListener { dismiss() }
        binding?.tvApply?.setOnClickListener {
            CallApplication.applyTheme.postValue(true)
            dismiss()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}