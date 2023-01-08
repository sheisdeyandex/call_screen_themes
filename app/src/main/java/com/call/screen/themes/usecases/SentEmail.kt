package com.call.screen.themes.usecases

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity


object SentEmail {
    fun sendEmail(message:String,activity:Activity?){
        val selectorIntent = Intent(Intent.ACTION_SENDTO)
        selectorIntent.data = Uri.parse("mailto:")

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("ksusha.kha.apps@gmail.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)
        emailIntent.selector = selectorIntent
        activity?.startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
    fun share(context: Context){
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+context.packageName)
        sendIntent.type = "text/plain"
        startActivity(context,sendIntent,null)
    }
}