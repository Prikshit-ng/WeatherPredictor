package com.prikshit.weatherdemo.remote.model

import com.google.gson.annotations.SerializedName

data class MainTempItem(
    @SerializedName("temp")val temp: String
)