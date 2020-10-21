package com.weather.model

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["name"])
data class WeatherModel(
    @field:SerializedName("base")
    val base: String = "",
    @field:SerializedName("clouds")
    @field:Embedded(prefix = "clouds_")
    val clouds: Clouds = Clouds(),
    @field:SerializedName("cod")
    val cod: Int = 0,
    @field:SerializedName("coord")
    @field:Embedded(prefix = "coord_")
    val coord: Coord = Coord(),
    @field:SerializedName("dt")
    val dt: Int = 0,
    @field:SerializedName("id")
    val id: Int = 0,
    @field:SerializedName("main")
    @field:Embedded(prefix = "main_")
    val main: Main = Main(),
    @field:SerializedName("name")
    val name: String = "",
    @field:SerializedName("sys")
    @field:Embedded(prefix = "sys_")
    val sys: Sys = Sys(),
    @field:SerializedName("timezone")
    val timezone: Int = 0,
    @field:SerializedName("visibility")
    val visibility: Int = 0,
    @field:SerializedName("wind")
    @field:Embedded(prefix = "wind_")
    val wind: Wind = Wind()
)

data class Clouds(
    @field:SerializedName("all")
    val all: Int = 0
)

data class Coord(
    @field:SerializedName("lat")
    val lat: Double = 0.0,
    @field:SerializedName("lon")
    val lon: Double = 0.0
)

data class Main(
    @field:SerializedName("feels_like")
    val feelsLike: Double = 0.0,
    @field:SerializedName("grnd_level")
    val grndLevel: Int = 0,
    @field:SerializedName("humidity")
    val humidity: Int = 0,
    @field:SerializedName("pressure")
    val pressure: Int = 0,
    @field:SerializedName("sea_level")
    val seaLevel: Int = 0,
    @field:SerializedName("temp")
    val temp: Double = 0.0,
    @field:SerializedName("temp_max")
    val tempMax: Double = 0.0,
    @field:SerializedName("temp_min")
    val tempMin: Double = 0.0
)

data class Sys(
    @field:SerializedName("country")
    val country: String = "",
    @field:SerializedName("sunrise")
    val sunrise: Int = 0,
    @field:SerializedName("sunset")
    val sunset: Int = 0
)

data class Wind(
    @field:SerializedName("deg")
    val deg: Int = 0,
    @field:SerializedName("speed")
    val speed: Double = 0.0
)