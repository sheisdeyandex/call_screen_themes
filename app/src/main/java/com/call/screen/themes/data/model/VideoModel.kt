package com.call.screen.themes.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoModel(
    @PrimaryKey
    val _id:String,
    val uri:String)