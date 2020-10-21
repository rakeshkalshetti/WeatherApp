package com.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.model.WeatherModel

@Database(entities = [WeatherModel::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}