package com.weather.repository

import androidx.lifecycle.LiveData
import com.weather.api.ApiService
import com.weather.db.WeatherDao
import com.weather.model.WeatherModel
import com.weather.util.AppExecutors
import com.weather.util.NetworkBoundResource
import com.weather.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val weatherDao: WeatherDao,
    private val githubService: ApiService
) {

    fun loadWeatherDetails(id: String, name: String): LiveData<Resource<WeatherModel>> {
        return object : NetworkBoundResource<WeatherModel, WeatherModel>(appExecutors) {
            override fun saveCallResult(item: WeatherModel) {
                if(item != null) {
                    weatherDao.insert(item)
                }
            }

            override fun shouldFetch(data: WeatherModel?) = data == null

            override fun loadFromDb() = weatherDao.load(
                name = name
            )

            override fun createCall() = githubService.getWeatherDetails(
                name = name,
                id = id
            )
        }.asLiveData()
    }
}
