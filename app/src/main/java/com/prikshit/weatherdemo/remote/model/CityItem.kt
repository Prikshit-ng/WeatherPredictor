package com.prikshit.weatherdemo.remote.model

import com.google.gson.annotations.SerializedName

data class CityItem(
    @SerializedName("name")val name: String,
    @SerializedName("sunrise")val sunrise: String,
    @SerializedName("sunset")val sunset: String
)