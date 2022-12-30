package com.call.screen.themes.viewmodels

import android.app.role.RoleManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.data.database.AppDatabase
import com.call.screen.themes.data.model.AdapterModel
import com.call.screen.themes.data.model.CallModel
import com.call.screen.themes.data.model.DataModel
import com.call.screen.themes.data.model.VideoModel
import com.call.screen.themes.interfaces.ApiInterface
import com.call.screen.themes.usecases.CheckboxUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainCallsViewModel : ViewModel() {
    val error = MutableLiveData<String>()
    var db: AppDatabase?= null
    var sharedPreferences :SharedPreferences? = null
    var roleManager:RoleManager?=null
    var count=0
    suspend fun getDataModelFromDb(): List<DataModel>? {
        val callsDao = db?.callsDao()
        return callsDao?.getAllDataModel()
    }
    suspend fun insertDb(dataModelList: List<DataModel>) {
        val callsDao = db?.callsDao()
        callsDao?.insertAllDataModel(dataModelList)
    }
    fun makeFreeRequest()= channelFlow {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val apiInterface = ApiInterface.create().getFreeCallVideos()
                apiInterface.enqueue(object : Callback<CallModel> {
                    override fun onResponse(call: Call<CallModel>, response: Response<CallModel>) {
                        response.body()?.let {callModel->
                           viewModelScope.launch {
                               withContext(Dispatchers.IO){
                                   makeAdapterList(callModel.data).collect{
                                       send(it)
                                       close()
                                   }
                                   insertDb(callModel.data)
                               }
                           }


                        }
                    }
                    override fun onFailure(call: Call<CallModel>, t: Throwable) {
                        count++
                        error.postValue(t.localizedMessage)
                    }
                })
            }

        }
        awaitClose()
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.Q)
    fun checkRolePermission(): Boolean? {
        return roleManager?.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
    }
    fun getFlashOnCall():Boolean?{
        return sharedPreferences?.getBoolean(Constants.flashOnCall,false)
    }
    fun setFlashOnCall(checked:Boolean){
        sharedPreferences?.edit()?.putBoolean(Constants.flashOnCall,checked)?.apply()
    }
    fun makeAdapterList(dataModelList:List<DataModel>)= channelFlow<ArrayList<AdapterModel>>{
        val newAdapterList = ArrayList<AdapterModel>()
        dataModelList.forEach {
            val random = Random.nextBoolean()
            if (random){
                newAdapterList.add(AdapterModel(it._id,it.title,it.theme,it.isPremium,it.__v,it.prevyu,R.drawable.profile_girl,"Rose",CheckboxUseCase.getCheckBox(it.isDownloaded)))
            }
            else{
                newAdapterList.add(AdapterModel(it._id,it.title,it.theme,it.isPremium,it.__v,it.prevyu,R.drawable.profile,"Adam",CheckboxUseCase.getCheckBox(it.isDownloaded)))

            }
         }.also {
            send(newAdapterList)
            close()
        }
        awaitClose()
    }.flowOn(Dispatchers.IO)
}