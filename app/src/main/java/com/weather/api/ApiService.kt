package com.weather.api

import androidx.lifecycle.LiveData
import com.weather.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/weather")
    fun getWeatherDetails(
        @Query("q") name: String,
        @Query("appid") id: String
    ): LiveData<ApiResponse<WeatherModel>>
}