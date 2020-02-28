package com.prikshit.weatherdemo.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_entity")
data class WeatherEntity(
    @PrimaryKey val date: String,
    val temp: String,
    val main: String,
    val desc: String,
    val icon: String,
    val cityName: String,
    val sunRise: String,
    val sunSet: String,
    val zipCode:String
)