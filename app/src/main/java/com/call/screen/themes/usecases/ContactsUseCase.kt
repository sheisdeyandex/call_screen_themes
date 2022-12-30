package com.call.screen.themes.usecases

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.call.screen.themes.data.model.ContactsModel


object ContactsUseCase {
    var contactList: ArrayList<ContactsModel> = ArrayList()
    private val PROJECTION = arrayOf(
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        ,
        ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
    )

    fun getContactList(context: Context):ArrayList<ContactsModel> {
        contactList = ArrayList()
        val cr: ContentResolver =context.contentResolver
        val cursor: Cursor? = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            PROJECTION,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        cursor?.use { cursor ->
            val nameIndex: Int = cursor.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME)
            val numberIndex: Int =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            var name: String
            var number: String
            while (cursor.moveToNext()) {
                val id: String = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                )
                val lookupKey = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY))
                val contactPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                name = cursor.getString(nameIndex)
                number = cursor.getString(numberIndex)
                number = number.replace(" ", "")
                contactList.add(ContactsModel(id= id,name =  name, number =  number, lookUpKey =  lookupKey,contactPhone))
            }
        }.also {
           return contactList
        }


    }
}