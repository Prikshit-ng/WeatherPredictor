package com.prikshit.weatherdemo.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.prikshit.weatherdemo.local.dao.WeatherDAO
import com.prikshit.weatherdemo.local.model.WeatherEntity
import com.prikshit.weatherdemo.remote.api.WeatherService
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class TodoRepository @Inject constructor(private val networkService:WeatherService, private val weatherDAO: WeatherDAO) {

    fun getWeatherData(zipCode: String): LiveData<List<WeatherEntity>> {
        return weatherDAO.getWeatherListLiveData(zipCode)
    }

    suspend fun fetchWeatherData(zipCode: String) {
        try {
            withTimeout(5_000) {
                networkService.getForecastList("$zipCode,IN").let {
                    if(!it.isSuccessful){
                        throw DataFetchError("Server Error", Exception())
                    }
                    if(it.body() == null) throw DataFetchError("No data", Exception())
                    if(it.body()?.cod != "200") throw DataFetchError("Error while fetching data", Exception())
                    it.body()!!
                }
            }.apply {
                weatherList.map {
                    WeatherEntity(it.date, it.main.temp, it.weather[0].main
                        , it.weather[0].description, it.weather[0].icon, city.name, city.sunrise,
                        city.sunset,zipCode)
                }.let {
                    weatherDAO.clearDB()
                    weatherDAO.insertWeather(it)
                }
            }
        } catch (error: Throwable) {
            throw DataFetchError("Unable to fetch data", error)
        }
    }

}
class DataFetchError(message: String, cause: Throwable) : Throwable(message, cause)

interface WeatherCallback {
    fun onStart()
    fun onCompleted()
    fun onError(cause: Throwable)
}