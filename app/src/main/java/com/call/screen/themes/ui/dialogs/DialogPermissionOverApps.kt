package com.call.screen.themes.ui.dialogs

import android.Manifest.permission.*
import android.app.Activity
import android.app.role.RoleManager
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.call.screen.themes.databinding.DialogPermissionOverAppsBinding
import com.call.screen.themes.databinding.DialogPermissionsBinding

class DialogPermissionOverApps:DialogFragment() {
    var binding: DialogPermissionOverAppsBinding? = null

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
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPermissionOverAppsBinding.inflate(inflater, container,false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding?.root
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        binding = DialogThemeNameBinding.inflate(layoutInflater)
//        val dialog = activity?.let {
//            Dialog(it)
//        }

//        return dialog!!
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(100)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}