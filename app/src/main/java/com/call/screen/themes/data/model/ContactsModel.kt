package com.call.screen.themes.data.model

import android.net.Uri

data class ContactsModel(val id:String, val name:String, val number:String,val lookUpKey:String, val photo:String?,val theme:String?,val visible:Boolean=false,val uri:String? = null)