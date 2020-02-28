package com.prikshit.weatherdemo.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prikshit.weatherdemo.local.model.WeatherEntity

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherList: List<WeatherEntity>)

    @Query("select * from weather_entity where zipCode = :zip order by date Asc")
    fun getWeatherListLiveData(zip:String): LiveData<List<WeatherEntity>>
    @Query("delete from weather_entity")
    suspend fun clearDB()
}