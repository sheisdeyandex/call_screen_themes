package com.call.screen.themes.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.usecases.ContactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsViewModel : ViewModel() {
   suspend fun getContacts(context: Context):List<ContactsModel>{
       return  ContactsUseCase.getContactList(context)

    }
}