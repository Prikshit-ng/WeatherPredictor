package com.prikshit.weatherdemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prikshit.weatherdemo.local.model.WeatherEntity
import com.prikshit.weatherdemo.repository.WeatherCallback
import com.prikshit.weatherdemo.repository.TodoRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    var appStateLiveData = MutableLiveData<AppState>()
    val responseStatus = MutableLiveData<Status>()
    var zipCode:String = ""

    init {
        appStateLiveData.postValue(AppState.SEARCH)
    }

    fun getWeatherData(): LiveData<List<WeatherEntity>> {
        return repository.getWeatherData(zipCode)
    }

    fun fetchWeatherList(zipCode: String){
        this.zipCode = zipCode
        fetchWeatherList(zipCode, object : WeatherCallback {
            override fun onStart() {
                responseStatus.postValue(Status.LOADING)
            }

            override fun onCompleted() {
                responseStatus.postValue(Status.COMPLETED)
            }

            override fun onError(cause: Throwable) {
                responseStatus.postValue(Status.ERROR)
            }
        })
    }
    private fun fetchWeatherList(zipCode:String, weatherCallback: WeatherCallback) {
        weatherCallback.onStart()
        viewModelScope.launch {
            try {
                repository.fetchWeatherData(zipCode)
                weatherCallback.onCompleted()
            } catch (throwable: Throwable) {
                weatherCallback.onError(throwable)
            }
        }
    }

    enum class Status{
        LOADING,
        COMPLETED,
        ERROR
    }
}
enum class AppState{
    SEARCH,
    DASH
}
