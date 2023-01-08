package com.call.screen.themes.viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.call.screen.themes.Constants
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.usecases.ContactsUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ContactThemesViewModel : ViewModel() {
    lateinit var sharedPrefs:SharedPreferences
    suspend fun getContacts(context: Context):List<ContactsModel>{
        return  ContactsUseCase.getContactList(context)
    }
    fun getArrayList(): ArrayList<ContactsModel> {
        val gson = Gson()
        val json = sharedPrefs.getString(Constants.Contacts, null)
        json?.let {
            val type = object : TypeToken<ArrayList<ContactsModel>>() {}.type
            return gson.fromJson(json, type)
        }?:return ArrayList()
    }
    fun saveArrayList(list:ArrayList<ContactsModel>) {
        val editor= sharedPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor?.putString(Constants.Contacts, json)
        editor?.apply()
    }
}