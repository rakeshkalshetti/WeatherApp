package com.weather.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.weather.model.WeatherModel
import com.weather.repository.WeatherRepository
import com.weather.util.AbsentLiveData
import com.weather.util.Resource

class WeatherViewModel @ViewModelInject constructor(repository: WeatherRepository) : ViewModel() {

    private val _weatherRequest: MutableLiveData<WeatherRequest> = MutableLiveData()
    val weatherRequest: LiveData<WeatherRequest>
        get() = _weatherRequest

    val repo: LiveData<Resource<WeatherModel>> = _weatherRequest.switchMap { input ->
        input.ifExists { id, name ->
            repository.loadWeatherDetails(id, name)
        }
    }

    fun setId(id: String, name: String) {
        val update = WeatherRequest(id, name)
        if (_weatherRequest.value == update) {
            return
        }
        _weatherRequest.value = update
    }

    data class WeatherRequest(val id: String, val name: String) {
        fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
            return if (id.isBlank() || name.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(id, name)
            }
        }
    }
}