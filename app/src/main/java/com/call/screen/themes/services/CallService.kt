package com.call.screen.themes.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.net.Uri
import android.os.Build
import android.telecom.TelecomManager
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.data.model.MainModel
import com.call.screen.themes.singleton.CallApplication
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CallService:LifecycleService() {
    private val layoutParamFlags = (WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
            or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)

    private var inflater: LayoutInflater? = null
    private var mDisplay: Display? = null
    private var layoutView: View? = null
    private var windowManager: WindowManager? = null
    lateinit var sharedPrefs: SharedPreferences
    var video: PlayerView? = null
    private var params: WindowManager.LayoutParams? = null
    var numberText = MutableLiveData<String>()
    var nameText = MutableLiveData<String>()
    var photoUri = MutableLiveData<String>()
    var btnAnswer:ImageView?=null
    var btnDecline:ImageView?=null
    var name:TextView?=null
    var number:TextView?=null
    private fun initViews(){
        btnAnswer = layoutView?.findViewById(R.id.btnAccept)
        btnDecline = layoutView?.findViewById(R.id.btnDecline)
        name = layoutView?.findViewById(R.id.tvName)
        number = layoutView?.findViewById(R.id.tvNumber)
    }

    private fun getArrayList(): ArrayList<ContactsModel> {
        val gson = Gson()
        val json = sharedPrefs.getString(Constants.Contacts, null)
        json?.let {
            val type = object : TypeToken<ArrayList<ContactsModel>>() {}.type
            return gson.fromJson(json, type)
        }?:return ArrayList()
    }
    private val Context.camaraManager: CameraManager? get() = getSystemService(CameraManager::class.java)
    private fun Context.toggleFlashLight(on: Boolean) {
        camaraManager?.run {
            val firstCameraWithFlash = cameraIdList.find { camera ->
                getCameraCharacteristics(camera).keys.any { it == FLASH_INFO_AVAILABLE }
            }

            firstCameraWithFlash?.let {
                setTorchMode(it, on)
            }}
    }
    private fun missedCall(){
        CallApplication.missedCall.observe(this){
            if (it){
                stopSelf()
            }
        }

    }
    private fun showPhoto(){
        val photo = layoutView?.findViewById<ImageView>(R.id.ivProfile)
        val cardPhoto = layoutView?.findViewById<CardView>(R.id.cvProfile)
        photoUri.observe(this){
            photo?.let { imageView ->
                if (it!=""){
                    Glide.with(this).load(it).into(imageView)
                    cardPhoto?.visibility = View.VISIBLE
                }
                else{

                }

            }
        }

    }
    var toggle = true
    private fun showFlashLight(){
        sharedPrefs?.getBoolean(Constants.flashOnCall,false)?.let {
            lifecycleScope.launch {
                if (it){
                    while (true){
                        delay(300)
                        toggle = !toggle
                        toggleFlashLight(toggle)
                    }
                }

            }
        }

    }
    private fun addViews(){
        params?.gravity = Gravity.TOP or Gravity.START
        windowManager = this.getSystemService(WINDOW_SERVICE) as WindowManager

        mDisplay = getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
        inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutView = inflater?.inflate(R.layout.call_screen, null)
        windowManager?.addView(layoutView, params)
    }

    private fun initSharedPrefs(){
        sharedPrefs = this.getSharedPreferences(Constants.sharedPrefsContactsName, MODE_PRIVATE)?:return
    }
    override fun onCreate() {
        super.onCreate()
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            layoutParamFlags,
            PixelFormat.TRANSLUCENT
        )
        addViews()
        initSharedPrefs()
        missedCall()
        initViews()
        showPhoto()
        showFlashLight()
        numberText.observe(this){numberObserved->
            val contactList = getArrayList()
            var contact = false
            video = layoutView?.findViewById(R.id.pVFullVideo)
            if (contactList.isNotEmpty()){
                contactList.forEach {
                    if (it.number==numberObserved){
                        initPlayer(Uri.parse(it.uri))
                        contact = true
                    }
                }.also {
                    if (!contact){
                        val mainUri =retrieveStoredObject(Constants.sharedPrefsContactsName, MainModel::class.java)?.uri
                        initPlayer(Uri.parse(mainUri))
                    }
                 }
             }
            else{
                val mainUri =retrieveStoredObject(Constants.sharedPrefsContactsName, MainModel::class.java)?.uri
                mainUri?.let {
                    initPlayer(Uri.parse(it))
                }?:stopSelf()

            }
            number?.text = numberObserved
        }
        val tm = getSystemService(TELECOM_SERVICE) as TelecomManager

         btnAnswer?.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ANSWER_PHONE_CALLS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }

            tm.acceptRingingCall()
            stopSelf()
        }
        nameText.observe(this){
            name?.text = it
        }
        btnDecline?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                tm.endCall()
                this.stopSelf()
            }
        }
//This is needed to keep the service running in background just needs a notification to call with startForeground();
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            "CHANNEL_ID", "ngh",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.setSound(null, null)
        notificationManager.createNotificationChannel(notificationChannel)
        val builder: Notification.Builder = Notification.Builder(this, "CHANNEL_ID")
        builder.setContentTitle("")
            .setContentText("")
            .setSmallIcon(R.drawable.ic_launcher_background)
        startForeground(1, builder.build())
    }
    private fun initPlayer(uri: Uri?){
        ExoPlayer.Builder(applicationContext)
            .build()
            .also { exoPlayer ->
                video?.player = exoPlayer
                val mediaItem = uri?.let {
                    MediaItem.fromUri(it)
                }
                mediaItem?.let {
                    exoPlayer.setMediaItem(it)
                }
                exoPlayer.prepare()
                exoPlayer.playWhenReady  = true
                exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
                Log.d("suka",uri.toString())
            }
    }
    fun <T> retrieveStoredObject(prefName: String, baseClass: Class<T>): T?{
        val dataObject: String? = sharedPrefs?.getString(prefName, "")
        return Gson().fromJson(dataObject, baseClass)
    }
    override fun onDestroy() {
        windowManager?.removeView(layoutView)
        toggle=false
        toggleFlashLight(false)
        super.onDestroy()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            numberText.postValue(it.getStringExtra(Constants.number))
            nameText.postValue(it.getStringExtra(Constants.name))
            it.getStringExtra(Constants.photo)?.let {
                photoUri.postValue(it)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

}