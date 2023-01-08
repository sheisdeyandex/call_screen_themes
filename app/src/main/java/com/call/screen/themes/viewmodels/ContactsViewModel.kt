package com.call.screen.themes.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.call.screen.themes.Constants
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.usecases.ContactsUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsViewModel : ViewModel() {
    lateinit var shared:SharedPreferences
   suspend fun getContacts(context: Context):List<ContactsModel>{
       return  ContactsUseCase.getContactList(context)
    }

    fun getArrayList(): ArrayList<ContactsModel> {
        val gson = Gson()
        val json = shared.getString(Constants.Contacts, null)
        json?.let {
            val type = object : TypeToken<ArrayList<ContactsModel>>() {}.type
            return gson.fromJson(json, type)
        }?:return ArrayList()
    }
}