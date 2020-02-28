package com.prikshit.weatherdemo.di

import android.app.Application
import com.prikshit.weatherdemo.local.WeatherDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalPersistenceModule {
    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    ) = WeatherDB.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun providesDAO(
        weatherDB: WeatherDB
    ) = weatherDB.getWeatherDao()
}