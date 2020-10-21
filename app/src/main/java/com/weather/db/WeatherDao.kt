package com.weather.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.weather.model.WeatherModel

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: WeatherModel)

    @Query("SELECT * FROM weathermodel WHERE name = :name")
    fun load(name: String): LiveData<WeatherModel>

    @Query("SELECT * FROM weathermodel")
    fun loadAll(): LiveData<List<WeatherModel>>

    @Delete
    fun delete(model: WeatherModel)

}