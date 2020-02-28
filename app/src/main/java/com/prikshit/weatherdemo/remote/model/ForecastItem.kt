package com.prikshit.weatherdemo.remote.model

import com.google.gson.annotations.SerializedName

data class ForecastItem(
    @SerializedName("id")val id: String,
    @SerializedName("main")val main: String,
    @SerializedName("description")val description: String,
    @SerializedName("icon")val icon: String
)