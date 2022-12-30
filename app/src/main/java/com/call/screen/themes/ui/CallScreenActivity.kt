package com.call.screen.themes.ui

import android.Manifest.permission.*
import android.app.Activity
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telecom.TelecomManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.databinding.CallScreenBinding
import com.call.screen.themes.services.CallUiService
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class CallScreenActivity : AppCompatActivity() {
    lateinit var binding: CallScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CallScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvNumber.text = intent.getStringExtra(Constants.number)
        val tm = getSystemService(TELECOM_SERVICE) as TelecomManager
        binding.btnAccept.setOnClickListener {
            tm.acceptRingingCall()
        }
    }
}