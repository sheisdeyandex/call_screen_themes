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
import com.call.screen.themes.data.model.AdapterModel
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.data.model.MainModel
import com.call.screen.themes.data.model.VideoModel
import com.google.gson.Gson
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

class IncomingCallScreenViewModel : ViewModel() {

}
