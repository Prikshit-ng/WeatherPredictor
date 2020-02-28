package com.prikshit.weatherdemo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prikshit.weatherdemo.ui.WeatherActivity
import com.prikshit.weatherdemo.ui.dash.WeatherFragment
import com.prikshit.weatherdemo.ui.factory.ViewModelFactory
import com.prikshit.weatherdemo.ui.search.ZipcodeFragment
import com.prikshit.weatherdemo.ui.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UIModule {

    @Binds
    abstract fun bindsViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindsWeatherViewModel(vm: WeatherViewModel): ViewModel

    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): WeatherActivity

    @ContributesAndroidInjector
    internal abstract fun contributesZipFrag(): ZipcodeFragment
    @ContributesAndroidInjector
    internal abstract fun contributesWeatherFrag(): WeatherFragment
}