package com.prikshit.weatherdemo.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("dt")val date: String,
    @SerializedName("main")val main: MainTempItem,
    @SerializedName("weather")val weather: List<ForecastItem>
)