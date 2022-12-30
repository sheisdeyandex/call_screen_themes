package com.call.screen.themes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.call.screen.themes.data.model.DataModel
import com.call.screen.themes.data.model.VideoModel

@Dao
interface CallsDao {
    @Query("SELECT * FROM videomodel")
    fun getAll(): List<VideoModel>

    @Query("SELECT * FROM videomodel WHERE _id = (:id)")
    fun loadById(id: String?): VideoModel

    @Insert
    fun insert(videoModel: VideoModel?)

    @Delete
    fun delete(videoModel: VideoModel)

    @Insert
    fun insertAllDataModel(dataModel: List<DataModel>?)
    @Query("SELECT * FROM datamodel")
    fun getAllDataModel(): List<DataModel>
    @Update
    fun updateDataModel(dataModel: DataModel)
}