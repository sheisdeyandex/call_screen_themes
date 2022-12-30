package com.call.screen.themes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.call.screen.themes.data.dao.CallsDao
import com.call.screen.themes.data.model.DataModel
import com.call.screen.themes.data.model.VideoModel

@Database(entities = [VideoModel::class,DataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun callsDao(): CallsDao
}