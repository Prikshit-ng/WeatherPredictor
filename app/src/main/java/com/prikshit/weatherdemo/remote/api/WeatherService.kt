package com.prikshit.weatherdemo.remote.api

import com.prikshit.weatherdemo.BuildConfig
import com.prikshit.weatherdemo.remote.model.WeatherResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast")
    suspend fun getForecastList(@Query("zip") zip: String,
                                @Query("appid") appid: String =BuildConfig.APP_ID,
                                @Query("units") units: String = "Metric"): Response<WeatherResponseItem>
}