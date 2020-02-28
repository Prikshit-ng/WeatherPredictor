package com.prikshit.weatherdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prikshit.weatherdemo.R
import com.prikshit.weatherdemo.ui.dash.WeatherFragment
import com.prikshit.weatherdemo.ui.factory.ViewModelFactory
import com.prikshit.weatherdemo.ui.search.ZipcodeFragment
import com.prikshit.weatherdemo.ui.viewmodel.AppState
import com.prikshit.weatherdemo.ui.viewmodel.WeatherViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)
        ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java).apply {
            appStateLiveData.observe(this@WeatherActivity, Observer {
                when(it){
                    AppState.SEARCH->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, ZipcodeFragment.newInstance())
                            .commitNow()
                    }
                    AppState.DASH->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, WeatherFragment.newInstance())
                            .commitNow()
                    }
                }
            })
        }
    }

}
