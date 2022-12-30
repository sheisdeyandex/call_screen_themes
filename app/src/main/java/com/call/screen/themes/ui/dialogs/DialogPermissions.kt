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
import com.call.screen.themes.R
import com.call.screen.themes.databinding.DialogPermissionsBinding

class DialogPermissions:DialogFragment() {
    private val resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            checkPermissions()
        }

    }
    private val permissionLauncher= registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result){
            checkPermissions()
        }

    }
    private val multiplePermissionLauncher= registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        checkPermissions()

    }
    var binding: DialogPermissionsBinding? = null
    var roleManager:RoleManager?=null
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
        binding = DialogPermissionsBinding.inflate(inflater, container,false)
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
        initClicks()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            roleManager= requireContext().getSystemService(AppCompatActivity.ROLE_SERVICE) as RoleManager
        }
        checkPermissions()

    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun checkRolePermission(): Boolean? {
        return roleManager?.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
    }
    private fun checkPermissions(){
        if ( ActivityCompat.checkSelfPermission(requireContext(), READ_CALL_LOG)==PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(requireContext(),
                READ_CONTACTS)==PackageManager.PERMISSION_GRANTED){
            binding?.btnSetPhoneNContacts?.isVisible= false
        }
        if (Settings.canDrawOverlays(requireContext())){
            binding?.btnScreenPermission?.isVisible= false
        }
        if ( ActivityCompat.checkSelfPermission(requireContext(), ANSWER_PHONE_CALLS)==PackageManager.PERMISSION_GRANTED){
            binding?.btnCallPermission?.isVisible= false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkRolePermission() == true){
                binding?.btnPhoneNumberPermission?.isVisible= false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun initClicks(){
        binding?.btnSetPhoneNContacts?.setOnClickListener {
            multiplePermissionLauncher.launch(arrayOf(READ_CALL_LOG, READ_CONTACTS))

        }
        binding?.btnScreenPermission?.setOnClickListener {
            openOverlaysPermissions()
//            it.findNavController().navigate(R.id.action_dialogPermissions_to_dialogPermissionOverApps)
        }
        binding?.btnCallPermission?.setOnClickListener {
            multiplePermissionLauncher.launch(arrayOf(ANSWER_PHONE_CALLS, READ_PHONE_STATE))
        }
        binding?.btnPhoneNumberPermission?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val intent = roleManager?.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
                resultLauncher.launch(intent)
            }
        }
        binding?.ivClose?.setOnClickListener {
            dismiss()
        }
//        binding?.mbOk?.setOnClickListener {
//            findNavController().navigate(R.id.action_dialogEnterThemeName_to_designContainerFragment)
//        }
//        binding?.mbCancel?.setOnClickListener {
//            dismiss()
//        }
    }
    private fun openOverlaysPermissions() {
        val intent =
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+requireContext().packageName))
        resultLauncher.launch(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}