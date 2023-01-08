package com.call.screen.themes.viewmodels

import android.app.DownloadManager
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.RoomDatabase
import com.call.screen.themes.Constants
import com.call.screen.themes.data.dao.CallsDao
import com.call.screen.themes.data.database.AppDatabase
import com.call.screen.themes.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.io.*
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class CallScreenViewModel : ViewModel() {
    var downloadManager:DownloadManager? = null
    lateinit var request:DownloadManager.Request
    var contactsList = ArrayList<ContactsModel>()
    lateinit var sharedPrefs:SharedPreferences
    var id: Long = 0
    var db: AppDatabase?= null
    var videoUri:Uri? = null
    fun storeToShow(dataObject: Any, prefName: String) {
        val dataObjectInJson = Gson().toJson(dataObject)
        val edit = sharedPrefs.edit()
        edit?.putString(prefName, dataObjectInJson)?.apply()
    }
    fun saveArrayList(list:ArrayList<ContactsModel>) {
        val editor= sharedPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor?.putString(Constants.Contacts, json)
        editor?.apply()
    }

    fun getArrayList(): ArrayList<ContactsModel> {
        val gson = Gson()
        val json = sharedPrefs.getString(Constants.Contacts, null)
        json?.let {
            val type = object : TypeToken<ArrayList<ContactsModel>>() {}.type
            return gson.fromJson(json, type)
        }?:return ArrayList()
    }
    fun <T> retrieveStoredObject(prefName: String, baseClass: Class<T>): T?{
        val dataObject: String? = sharedPrefs.getString(prefName, "")
        return Gson().fromJson(dataObject, baseClass)
    }
    fun isApplied(id:String?):Boolean{
        return retrieveStoredObject(Constants.sharedPrefsContactsName, MainModel::class.java)?.id!=id
    }
    suspend fun getVideoFromDb(id: String?): VideoModel? {
        val callsDao = db?.callsDao()
        return callsDao?.loadById(id)
    }
    suspend fun insertDb(videoModel: VideoModel?) {
        val callsDao = db?.callsDao()
        callsDao?.insert(videoModel)
    }
    suspend fun updateDb(dataModel: DataModel) {
        val callsDao = db?.callsDao()
        callsDao?.updateDataModel(dataModel)
    }
    fun retrieve( fileName: String?,
                  desc: String,
                  url: String?,
                  context: Context) = flow {
        // fileName -> fileName with extension
        request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle(fileName)
            .setDescription(desc)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        downloadManager= context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        id = downloadManager!!.enqueue(request)
        val downloading = AtomicBoolean(true)

        while (downloading.get()) {
            id.let {
                val query = DownloadManager.Query().setFilterById(it)
                val cursor = downloadManager!!.query(query)

                cursor.moveToFirst()

                val bytesDownloaded = cursor.intValue(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                val bytesTotal = cursor.intValue(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)

                if (isSuccessful(cursor)) downloading.set(false)

                cursor.close()

                emit(DownloadingState.Downloading(bytesDownloaded, bytesTotal))

                if (downloading.get()) delay(1000)
            }

        }
    }.flowOn(Dispatchers.IO)
}
private fun isSuccessful(cursor: Cursor) = status(cursor) == DownloadManager.STATUS_SUCCESSFUL
private fun status(cursor: Cursor) = cursor.intValue(DownloadManager.COLUMN_STATUS)
private fun Cursor.column(which: String) = this.getColumnIndex(which)
private fun Cursor.intValue(which: String): Int = this.getInt(column(which))

sealed class DownloadingState {
    data class Downloading(val downloadedBytes: Int, val totalBytes: Int) : DownloadingState() {
        val progress = if (totalBytes == 0) 0 else ((downloadedBytes * 100) / totalBytes)
    }
    object Failure : DownloadingState()
}
