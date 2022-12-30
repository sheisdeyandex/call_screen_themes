package com.call.screen.themes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataModel(@PrimaryKey val _id:String, val title:String, val theme:String, val isPremium:Boolean, val __v:String, val prevyu:String, val isDownloaded:Boolean = false)