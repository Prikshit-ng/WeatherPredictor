package com.prikshit.weatherdemo.local

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prikshit.weatherdemo.local.dao.WeatherDAO
import com.prikshit.weatherdemo.local.model.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDB : RoomDatabase() {
    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "weather_ng.db"
        @Volatile
        private var INSTANCE: WeatherDB? = null

        fun getInstance(@NonNull context: Context): WeatherDB {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            WeatherDB::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun getWeatherDao(): WeatherDAO
}