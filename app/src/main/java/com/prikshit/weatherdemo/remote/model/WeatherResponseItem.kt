package com.prikshit.weatherdemo.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponseItem(
    @SerializedName("cod")val cod: String,
    @SerializedName("list")val weatherList: List<WeatherItem>,
    @SerializedName("city")val city: CityItem
)